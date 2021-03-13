import java.awt.*
import java.awt.image.BufferedImage
import javax.swing.ImageIcon

object IconCreator {
    fun create(v: Graph.Vertex) : ImageIcon = ImageIcon(BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB).apply {
        val g2d = graphics as Graphics2D
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
        g2d.color = Color.WHITE
        g2d.fillRect(0, 0, width, height)
        g2d.color = v.color
        g2d.fillOval(0, 0, 31, 31)
        g2d.setXORMode(Color.WHITE)
        g2d.font = Font("Cambria", Font.PLAIN, 16)
        when(v.id){
            in 0..8 -> g2d.drawString((v.id + 1).toString(), 11, 22)
            else -> g2d.drawString((v.id + 1).toString(), 7, 22)
        }
    })
}