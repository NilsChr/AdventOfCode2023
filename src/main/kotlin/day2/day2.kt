package day2

import readFile

data class CubeSet(var red: Int = 0, var green: Int = 0, var blue: Int = 0)

fun main() {
    val startTime = System.nanoTime()
    println("Running day2")
    val lines = readFile("day2/input")
    var task1 = 0
    var task2 = 0
    lines.forEach {
        val game = parseLine(it)
        if (game.isValid(12, 13, 14)) task1 += game.id
        task2 += game.getMinCubes()
    }
    println("Task 1 res: $task1")
    println("Task 2 res: $task2")
    val endTime = System.nanoTime()
    val elapsedTime = endTime - startTime
    val elapsedSeconds = elapsedTime / 1_000_000_000.0 // Convert nanoseconds to seconds
    println("Elapsed Time: $elapsedSeconds seconds")
}

class Game(val id: Int) {
    var cubeSets: List<CubeSet> = emptyList()
    fun isValid(red: Int, green: Int, blue: Int): Boolean {
        return cubeSets.all { it.red <= red && it.green <= green && it.blue <= blue }
    }

    fun getMinCubes(): Int {
        val red = cubeSets.maxByOrNull { it.red }?.red ?: 1
        val green = cubeSets.maxByOrNull { it.green }?.green ?: 1
        val blue = cubeSets.maxByOrNull { it.blue }?.blue ?: 1
        return red * green * blue
    }
}

fun parseLine(line: String): Game {
    val parts = line.split(":", ";")
    val id = parts[0].split(" ")[1].toInt()
    val game = Game(id)
    parts.forEachIndexed { index, part ->
        if (index == 0) return@forEachIndexed
        val set = CubeSet()
        val colors = part.split(",")
        colors.forEach {
            val colorParts = it.trim().split(" ")
            if (colorParts.isEmpty()) return@forEachIndexed
            if (colorParts[1] == "red") set.red = colorParts[0].toInt()
            if (colorParts[1] == "green") set.green = colorParts[0].toInt()
            if (colorParts[1] == "blue") set.blue = colorParts[0].toInt()
        }
        game.cubeSets += set
    }
    return game
}
