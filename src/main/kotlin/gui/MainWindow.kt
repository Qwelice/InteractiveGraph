package gui

import Graph
import IconCreator
import gui.painters.GraphPainter
import util.ConvertPlane
import util.Converter
import java.awt.Color
import java.awt.ComponentOrientation
import java.awt.Dimension
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.*
import javax.swing.border.EtchedBorder

class MainWindow(private var a: Graph) : JFrame() {
    private val minFrameSize = Dimension(500, 500)
    private val graphicsPanel = GraphicsPanel()
    private val dlm = DefaultListModel<ImageIcon>()
    private val images = JList(dlm)
    private val pane = JScrollPane(images)
    private val plane: ConvertPlane
    private val painter: GraphPainter

    init{
        minimumSize = minFrameSize
        defaultCloseOperation = EXIT_ON_CLOSE
        isVisible = true
        title = "Graph"
        graphicsPanel.background = Color.WHITE
        graphicsPanel.border = EtchedBorder()
        addGraph(a)
        val gl = GroupLayout(contentPane)
        gl.setHorizontalGroup(gl.createSequentialGroup()
            .addGap(5)
            .addGroup(gl.createParallelGroup()
                .addComponent(pane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                .addGap(5)
                .addComponent(graphicsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
            )
            .addGap(5)
        )
        gl.setVerticalGroup(gl.createSequentialGroup()
            .addGap(5)
            .addComponent(pane, 70, GroupLayout.PREFERRED_SIZE, 90)
            .addGap(5)
            .addComponent(graphicsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
            .addGap(5)
        )
        layout = gl
        pack()

        plane = ConvertPlane(graphicsPanel.width, graphicsPanel.height, -10.0, 10.0, -10.0, 10.0)
        painter = GraphPainter(plane)
        graphicsPanel.addPainter(painter)
        painter.graph = a
        graphicsPanel.addMouseListener(object : MouseListener{
            override fun mouseClicked(e: MouseEvent?) {
                if(!images.isSelectionEmpty && e != null && e.button == MouseEvent.BUTTON1){
                    val px = Converter.xScr2Crt(e.x, plane)
                    val py = Converter.yScr2Crt(e.y, plane)
                    a.vertices[images.selectedIndex].apply {
                        changeLocation(px, py)
                        onScreen = true
                        graphicsPanel.repaint()
                    }
                }
            }

            override fun mousePressed(e: MouseEvent?) {

            }

            override fun mouseReleased(e: MouseEvent?) {

            }

            override fun mouseEntered(e: MouseEvent?) {

            }

            override fun mouseExited(e: MouseEvent?) {

            }

        })

        addComponentListener(object : ComponentAdapter(){
            override fun componentResized(e: ComponentEvent?) {
                plane.width = graphicsPanel.width
                plane.height = graphicsPanel.height
                graphicsPanel.repaint()
            }
        })
    }

    private fun addGraph(a: Graph) {
        for(x in a.vertices) dlm.add(dlm.size(), IconCreator.create(x))
    }
}