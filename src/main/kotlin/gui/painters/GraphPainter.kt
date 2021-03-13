package gui.painters

import Graph
import util.ConvertPlane
import util.Converter
import java.awt.*
import kotlin.math.atan

class GraphPainter(private val plane: ConvertPlane) : Painter() {
    var graph : Graph? = null
    var mousePos : Point = Point(0, 0)
    override fun draw(g: Graphics) {
        drawVertices(g)
    }

    private fun drawVertices(g: Graphics){
        if(graph != null){
            var dx : Int
            var dy : Int
            val g2d = g as Graphics2D
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            g2d.stroke = BasicStroke(2.0f)
            for(i in 0 until graph!!.vertices.size ){
                val v : Graph.Vertex= graph!!.vertices[i]
                if(v.onScreen){
                    dx = Converter.xCrt2Scr(v.x, plane)
                    dy = Converter.yCrt2Scr(v.y, plane)
                    g2d.setPaintMode()
                    for(j in i until graph!!.vertices.size){
                        if(v.isIncidents(j) && graph!!.vertices[j].onScreen) {
                            val avg = graph!!.vertices.let {
                                val maxAvg = mutableListOf<Double>()
                                it.forEach { that ->
                                    maxAvg.add(that.incidents.values.minOrNull()!!)
                                }
                                maxAvg.maxOrNull()!!
                            }
                            g2d.color = when(v.incidents[j]!!){
                                in (0.0)..(avg) -> Color.GREEN
                                in (avg + 0.1)..(1.5 * avg) -> Color.YELLOW
                                else -> Color.RED
                            }
                            drawEdge(g2d, v.x, v.y, graph!!.vertices[j].x, graph!!.vertices[j].y)
                        }
                    }
                    g2d.color = v.color
                    g2d.fillOval(dx - 16, dy - 16, 32, 32)
                    if(v.isInVertex(Converter.xScr2Crt(mousePos.x, plane), Converter.yScr2Crt(mousePos.y, plane))){
                        g2d.color = Color.CYAN
                        g2d.drawOval(dx - 16, dy - 16, 32, 32)
                    }
                    else{
                        g2d.color = Color.GRAY
                        g2d.drawOval(dx - 16, dy - 16, 32, 32)
                    }
                    g2d.color = v.color
                    g2d.setXORMode(Color.WHITE)
                    g2d.font = Font("TimesRoman", Font.PLAIN, 16)
                    when(v.id){
                        in 0..8 -> g2d.drawString((v.id + 1).toString(), dx - 16 + 11, dy - 16 + 22)
                        else -> g2d.drawString((v.id + 1).toString(), dx - 16 + 7, dy - 16 + 22)
                    }
                    g2d.setPaintMode()
                    for(j in i until graph!!.vertices.size){
                        if(v.isIncidents(j) && graph!!.vertices[j].onScreen) {
                            drawNumbers(g2d, v.x, v.y, graph!!.vertices[j].x, graph!!.vertices[j].y, v.incidents[j]!!)
                        }
                    }
                }
            }
        }
    }

    private fun drawNumbers(g: Graphics2D, x1: Double, y1: Double, x2: Double, y2: Double, w: Double){
        g.font = Font("Cambria", Font.PLAIN, 14)
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        val x = (x1 + x2) / 2
        val y = (y1 + y2) / 2
        val alpha = atan(((x1 - x2) / 2) / ((y1 - y2) / 2))
        g.rotate(alpha - Math.PI / 2 + Math.PI,
            Converter.xCrt2Scr(x, plane).toDouble(), Converter.yCrt2Scr(y, plane).toDouble())
        g.color = Color.BLUE
        g.setXORMode(Color.WHITE)
        g.drawString(w.toString(), Converter.xCrt2Scr(x, plane),
            Converter.yCrt2Scr(y, plane) - 3)
        g.setPaintMode()
        g.rotate(-(alpha - Math.PI / 2 + Math.PI),
            Converter.xCrt2Scr(x, plane).toDouble(), Converter.yCrt2Scr(y, plane).toDouble())
    }

    private fun drawEdge(g: Graphics, x1: Double, y1: Double, x2: Double, y2: Double){
        val g2d = g as Graphics2D
        g2d.stroke = BasicStroke(2f)
        g2d.drawLine(Converter.xCrt2Scr(x1, plane), Converter.yCrt2Scr(y1, plane),
            Converter.xCrt2Scr(x2, plane), Converter.yCrt2Scr(y2, plane))
    }

    fun updatePosition(x: Int, y: Int){
        mousePos.x = x
        mousePos.y = y
    }

    override fun update(w: Int, h: Int) {

    }
}