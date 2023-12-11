import java.io.File
import java.io.BufferedReader
import java.io.FileReader

fun readFile(fileName: String): List<String> {
    return File("./input/$fileName.txt").readLines()
}
fun readFile2(fileName: String): Array<String> {
    val file = File("./input/$fileName.txt")

    return try {
        file.readLines().toTypedArray()
    } catch (e: Exception) {
        e.printStackTrace()
        emptyArray()
    }
}

fun readLinesUsingBufferedReader(fileName: String): Array<String> {
    val file = File("./input/$fileName.txt")

    return try {
        BufferedReader(FileReader(file)).use { reader ->
            val lines = mutableListOf<String>()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                lines.add(line!!)
            }
            lines.toTypedArray()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        emptyArray()
    }
}


fun readFileToGrid(fileName: String): Array<Array<Char>> {
    val fileContent = readLinesUsingBufferedReader(fileName) //File("./input/$fileName.txt").readText()
    return fileContent.map{ line -> line.map { it }.toTypedArray<Char>()}.toTypedArray()
    //return fileContent.lines().map{ line -> line.map { it }.toTypedArray<Char>()}.toTypedArray()
}