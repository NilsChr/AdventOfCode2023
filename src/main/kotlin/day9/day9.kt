package day9

import readLinesUsingBufferedReader
import stringToIntArray

fun main() {
    val startTime = System.nanoTime()
    println("Running day 9")
    val lines = readLinesUsingBufferedReader("day9/input")
    val score = combo(lines)
    println("Task 1 res: ${score.first}")
    println("Task 2 res: ${score.second}")
    val endTime = System.nanoTime()
    val elapsedTime = endTime - startTime
    val elapsedSeconds = elapsedTime / 1_000_000_000.0 // Convert nanoseconds to seconds
    println("Elapsed Time: $elapsedSeconds seconds")
}

fun combo(lines: Array<String>): Pair<Int, Int> {
    var part1 = 0
    var part2 = 0
    lines.forEach { line ->
        val inputArray = stringToIntArray(line).toMutableList()
        val matrix  = mutableListOf<MutableList<Int>>()
        matrix.add(inputArray)
        var between = inputArray.zipWithNext{ a, b -> b - a }.toMutableList()
        do {
            matrix.add(between)
            between =  between.zipWithNext{ a, b -> b - a }.toMutableList()
        } while(!between.all { it == 0 })
        for(i in matrix.size-2 downTo 0) {
            matrix[i].add(matrix[i].last() + matrix[i + 1].last())
            matrix[i].add(0,matrix[i].first() - matrix[i + 1].first())
        }
        part1 += matrix[0].last()
        part2 += matrix[0].first()
    }
    return Pair(part1, part2)
}