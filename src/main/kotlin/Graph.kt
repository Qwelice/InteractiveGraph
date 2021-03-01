import java.awt.Color
import java.util.*

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

    class Vertex(n: Int){
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
    }
}