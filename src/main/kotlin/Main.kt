import gui.MainWindow

fun main(){
    val mtx = CSVReader.read("D:\\KotlinProjects\\InteractiveGraph\\src\\csv\\matrix1.csv")
    printMatrix(mtx)
    val g = Graph(mtx)
    println()
    printGraph(g)
    val window = MainWindow(g)
}

fun printMatrix(a: MutableList<List<Double>>){
    for(x in a) println(x.joinToString(" ", "[", "]"))
}

fun printGraph(g: Graph){
    for(x in g.vertices) println(x.incidents)
}