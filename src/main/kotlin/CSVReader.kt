import java.io.*

class CSVReader{
    companion object{
        fun read(fileName : String, delimiter: String = ";"): MutableList<List<Double>>{
            val reader = BufferedReader(InputStreamReader(FileInputStream(fileName), "UTF-8"))
            return mutableListOf<List<Double>>().apply {
                try{
                    while(reader.readLine().also {
                        if(it != null) this.add(it.split(delimiter).map { that -> that.toDouble() })
                    } != null){ }
                }catch (e: Exception){println("Ошибка при чтении файла")}
            }
        }
    }
}