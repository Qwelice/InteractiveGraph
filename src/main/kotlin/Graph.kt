import util.ConvertPlane
import util.Converter
import java.awt.Color
import java.util.*
import kotlin.math.*

class Graph(matrix: MutableList<List<Double>>){
    companion object{
        private val r = Random()
    }

    val vertices = mutableListOf<Vertex>().apply {
        for(i in matrix.indices){
            for(j in matrix[i].indices){
                if(j !in this.indices)  add(Vertex(j))
                if(i != j && matrix[i][j] != 0.0){
                    this[i].addIncident(j, matrix[i][j])
                    this[j].addIncident(i, matrix[j][i])
                }
            }
        }
    }

    fun isInVertices(x: Double, y: Double) : Int{
        for(v in vertices){
            if(v.isInVertex(x, y))
                return v.id
        }
        return -1
    }

    fun centerVertices(plane: ConvertPlane) {
        if(vertices.size != 0){
            val d = (min(abs(plane.xMax - plane.xMin), abs(plane.yMax - plane.yMin)) / 2) * 0.90
            val x = plane.xMin + abs(plane.xMax - plane.xMin) / 2
            val y = plane.yMin + abs(plane.yMax - plane.yMin) / 2
            val alpha = 2 * Math.PI / vertices.size
            for(i in vertices.indices){
                vertices[i].onScreen = true
                vertices[i].changeLocation(x + d * cos(alpha * i), y + d * sin(alpha * i))
            }
        }
    }

    class Vertex(n: Int){
        private val radius: Double = 1.0
        var onScreen: Boolean = false
        val id: Int = n
        val incidents = mutableMapOf<Int, Double>()
        var x = 0.0
            private set
        var y = 0.0
            private set
        val color = Color(r.nextInt(256), r.nextInt(256), r.nextInt(256))
        fun isIncidents(v: Int) : Boolean = v in incidents
        fun addIncident(v: Int, w: Double) : Vertex = apply { incidents[v] = w }
        fun removeIncident(v: Int) : Vertex = apply { incidents.remove(v) }
        fun changeLocation(x: Double, y: Double){
            this.x = x
            this.y = y
        }
        fun isInVertex(x: Double, y: Double) : Boolean = sqrt((this.x - x).pow(2.0) + (this.y - y).pow(2.0)) <= radius
    }
}