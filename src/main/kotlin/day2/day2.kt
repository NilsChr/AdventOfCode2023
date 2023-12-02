package day2

import fileToArray

data class CubeSet(var red: Int = 0, var green: Int = 0, var blue: Int = 0)

fun main() {
    val startTime = System.nanoTime()

    println("Running day2")
    val lines = fileToArray("day2/input")

    println("Task 1 res: ${task1(lines)}")
    println("Task 2 res: ${task2(lines)}")

    val endTime = System.nanoTime()
    val elapsedTime = endTime - startTime
    val elapsedSeconds = elapsedTime / 1_000_000_000.0 // Convert nanoseconds to seconds
    println("Elapsed Time: $elapsedSeconds seconds")
}

class Game(val id: Int) {
    var cubeSets: List<CubeSet> = emptyList()
    fun isValid(red: Int, green: Int, blue: Int): Boolean {
        cubeSets.forEach {
            if (it.red > red) return false
            if (it.green > green) return false
            if (it.blue > blue) return false
        }
        return true
    }

    fun getMinCubes(): Int {
        var red = 0
        var green = 0
        var blue = 0
        cubeSets.forEach {
            if (it.red > red) red = it.red
            if (it.green > green) green = it.green
            if (it.blue > blue) blue = it.blue
        }
        return red * green * blue
    }

    override fun toString(): String {
        return "Id: $id"
    }
}

fun task1(input: Array<String>): Int {
    var sum = 0
    input.forEach {
        val game = parseLine(it)
        if (game.isValid(12, 13, 14)) sum += game.id
    }
    return sum
}

fun task2(input: Array<String>): Int {
    var sum = 0
    input.forEach {
        val game = parseLine(it)
        sum += game.getMinCubes()
    }
    return sum
}

fun parseLine(line: String): Game {
    val parts = line.split(":", ";")
    val id = extractNumber(parts[0])
    val game = Game(id)
    parts.forEachIndexed { index, part ->
        if (index == 0) return@forEachIndexed
        val set = CubeSet()
        val colors = part.split(",")
        colors.forEach {
            if (it.contains("red")) set.red = extractNumber(it)
            if (it.contains("green")) set.green = extractNumber(it)
            if (it.contains("blue")) set.blue = extractNumber(it)
        }
        game.cubeSets += set
    }
    return game
}

fun extractNumber(input: String): Int {
    val regex = Regex(pattern = "\\d+")
    val matchResult = regex.find(input)
    val number = matchResult?.value?.toIntOrNull()
    if (number != null) return number
    return 0
}