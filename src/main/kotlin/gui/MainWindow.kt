package gui

import Graph
import IconCreator
import gui.painters.GraphPainter
import util.ConvertPlane
import util.Converter
import java.awt.Color
import java.awt.ComponentOrientation
import java.awt.Dimension
import java.awt.event.*
import javax.swing.*
import javax.swing.border.EtchedBorder

class MainWindow(private var a: Graph) : JFrame() {
    private val minFrameSize = Dimension(600, 720)
    private val graphicsPanel = GraphicsPanel()
    private val dlm = DefaultListModel<ImageIcon>()
    private val images = JList(dlm).apply { isFocusable = false }
    private val pane = JScrollPane(images).apply { isFocusable = false }
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

        addKeyListener(object : KeyListener{
            override fun keyTyped(e: KeyEvent?) {

            }

            override fun keyPressed(e: KeyEvent?) {
                if(e != null && e.keyCode == KeyEvent.VK_F){
                    painter.graph!!.centerVertices(plane)
                    graphicsPanel.repaint()
                }
            }

            override fun keyReleased(e: KeyEvent?) {

            }

        })
        graphicsPanel.addMouseListener(object : MouseListener{
            override fun mouseClicked(e: MouseEvent?) {
                if(e != null){
                    val px = Converter.xScr2Crt(e.x, plane)
                    val py = Converter.yScr2Crt(e.y, plane)
                    if(e.button == MouseEvent.BUTTON3 && painter.graph != null){
                        when(val i = painter.graph!!.isInVertices(px, py)){
                            in painter.graph!!.vertices.indices -> painter.graph!!.vertices[i].onScreen = false
                            else -> { }
                        }
                        graphicsPanel.repaint()
                    }
                    if(e.button == MouseEvent.BUTTON1){
                        when(val i = painter.graph!!.isInVertices(px, py)){
                            in painter.graph!!.vertices.indices -> images.selectedIndex = i
                            else ->{
                                a.vertices[images.selectedIndex].apply {
                                    changeLocation(px, py)
                                    onScreen = true
                                    graphicsPanel.repaint()
                                }
                            }
                        }
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
        graphicsPanel.addMouseMotionListener(object : MouseMotionListener{
            override fun mouseDragged(e: MouseEvent?) {
                if(e != null){
                    val px = Converter.xScr2Crt(e.x, plane)
                    val py = Converter.yScr2Crt(e.y, plane)
                    if(e.button == MouseEvent.BUTTON1 && painter.graph != null){
                        when(val i = painter.graph!!.isInVertices(px, py)){
                            in painter.graph!!.vertices.indices -> painter.graph!!.vertices[i].changeLocation(px, py)
                            else -> { }
                        }
                        graphicsPanel.repaint()
                    }
                }
            }

            override fun mouseMoved(e: MouseEvent?) {
                if(e != null){
                    painter.updatePosition(e.x, e.y)
                    graphicsPanel.repaint()
                }
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