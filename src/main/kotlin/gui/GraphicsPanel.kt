package gui

import gui.painters.Painter
import java.awt.Graphics
import javax.swing.JPanel

class GraphicsPanel : JPanel() {
    private val painters = mutableListOf<Painter>()

    fun addPainter(p: Painter) = painters.add(p)
    fun removePainter(p: Painter) = painters.remove(p)
    override fun paint(g: Graphics){
        super.paint(g)
        painters.forEach { it.draw(g) }
    }
}