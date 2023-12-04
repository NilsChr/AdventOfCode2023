import java.io.File

fun readFile(fileName: String): List<String> {
    return File("./input/$fileName.txt").readLines()
}

fun readFileToGrid(fileName: String): Array<Array<Char>> {
    val fileContent = File("./input/$fileName.txt").readText()
    return fileContent.lines().map{ line -> line.map { it }.toTypedArray<Char>()}.toTypedArray()
}