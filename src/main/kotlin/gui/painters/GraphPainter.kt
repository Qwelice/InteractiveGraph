package gui.painters

import Graph
import util.ConvertPlane
import util.Converter
import java.awt.Color
import java.awt.Font
import java.awt.Graphics

class GraphPainter(private val plane: ConvertPlane) : Painter() {
    var graph : Graph? = null
    override fun draw(g: Graphics) {
        drawVertices(g)
    }

    private fun drawVertices(g: Graphics){
        if(graph != null){
            for(v in graph!!.vertices){
                var dx: Int
                var dy: Int
                g.setPaintMode()
                if(v.onScreen){
                    dx = Converter.xCrt2Scr(v.x, plane)
                    dy = Converter.yCrt2Scr(v.y, plane)
                    for(i in v.incidents.keys){
                        val p = graph!!.vertices[i]
                        if(p.onScreen && v.incidents[i]!![1] == 0.0){
                            val px = Converter.xCrt2Scr(p.x, plane)
                            val py = Converter.yCrt2Scr(p.y, plane)
                            when(v.incidents[i]!![0]){
                                in (0.0)..(5.9) -> g.color = Color.GREEN
                                in (6.0)..(8.9) -> g.color = Color.YELLOW
                                else -> g.color = Color.RED
                            }
                            g.drawLine(dx, dy, px, py)
                            v.incidents[i]!![1] = 0.0
                            graph!!.vertices[i].incidents[v.id]!![1] = 1.0
                        }
                    }
                    g.color = v.color
                    g.fillOval(dx - 16, dy - 16, 32, 32)
                    g.setXORMode(Color.WHITE)
                    g.font = Font("TimesRoman", Font.PLAIN, 16)
                    g.drawString(v.id.toString(), dx -16 + 11, dy - 16 + 21)
                }
            }
        }
    }

    override fun update(w: Int, h: Int) {

    }
}