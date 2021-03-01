import java.awt.*
import java.awt.image.BufferedImage
import javax.swing.ImageIcon

object IconCreator {
    fun create(v: Graph.Vertex) : ImageIcon = ImageIcon(BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB).apply {
        val g2D = graphics as Graphics2D
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g2D.color = Color.WHITE
        g2D.fillRect(0, 0, width, height)
        g2D.color = v.color
        g2D.fillOval(0, 0, 31, 31)
        g2D.setXORMode(Color.WHITE)
        g2D.font = Font("TimesRoman", Font.PLAIN, 16)
        g2D.drawString(v.id.toString(), 11, 21)
    })
}