import java.io.File
import java.io.InputStream

fun fileToArray(fileName: String): Array<String> {
    var output: Array<String> = emptyArray()
    val inputStream: InputStream = File("./input/$fileName.txt").inputStream()
    val lineList = mutableListOf<String>()
    inputStream.bufferedReader().forEachLine { lineList.add(it) }
    lineList.forEach{output += it}
    return output
}

