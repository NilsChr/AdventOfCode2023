import java.io.File

fun readFile(fileName: String): List<String> {
    return File("./input/$fileName.txt").readLines()
}

fun readFileToGrid(fileName: String): Array<Array<Char>> {
    // Read the contents of the file
    val fileContent = File("./input/$fileName.txt").readText()
    //return fileContent.lines().map { it.split("").toTypedArray<String>() }.toTypedArray<Array<String>>();
    return fileContent.lines().map{ line -> line.map { it }.toTypedArray<Char>()}.toTypedArray()
}