package template

import readLinesUsingBufferedReader
import stringToIntArray

fun main() {
    val startTime = System.nanoTime()
    println("Running day 6")
    val lines = readLinesUsingBufferedReader("day6/input")
    val data = parseInput(lines)
    val task1 = part1(data)
    val task2 = part2(data)
    println("Task 1 res: $task1")
    println("Task 2 res: $task2")
    val endTime = System.nanoTime()
    val elapsedTime = endTime - startTime
    val elapsedSeconds = elapsedTime / 1_000_000_000.0 // Convert nanoseconds to seconds
    println("Elapsed Time: $elapsedSeconds seconds")
}

fun part1(pair: Pair<Array<Int>, Array<Int>>): Int {
    val valid: MutableList<Int> = mutableListOf()

    pair.first.forEachIndexed { i, time ->
        val records: MutableList<Int> = mutableListOf()
        for (hold in 1..<time) {
            val distance = calcDistance(time, hold)
            if (distance > pair.second[i]) {
                records += hold
            }
        }
        valid += records.size
    }
    return valid.reduce { acc, i -> acc * i }
}

fun part2(pair: Pair<Array<Int>, Array<Int>>): Int {
    val time = pair.first.joinToString("") { it.toString() }.toLong()
    val distance = pair.second.joinToString("") { it.toString() }.toLong()
    var records = 0;
    for (hold in 1..<time) {
        val d = calcDistanceLong(time, hold)
        if (d > distance) {
            records++;
        }
    }
    return records
}


fun parseInput(lines: Array<String>): Pair<Array<Int>, Array<Int>> {
    val times: Array<Int> = stringToIntArray(lines[0].replace("time:", ""))
    val distances: Array<Int> = stringToIntArray(lines[1].replace("distance:", ""))
    return Pair(times, distances)
}

fun calcDistance(time: Int, hold: Int): Int {
    //val rest = time % hold
    val ticks = time - hold
    return hold * ticks
}

fun calcDistanceLong(time: Long, hold: Long): Long {
    //val rest = time % hold
    val ticks = time - hold
    return hold * ticks
}