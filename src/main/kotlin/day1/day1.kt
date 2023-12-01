package day1

import fileToArray

val numberStrings = arrayOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

fun main() {
    val startTime = System.nanoTime()

    println("Running day1")
    val lines = fileToArray("day1")

    println("Task 1_2 res: ${task1(lines)}")
    println("Task 2_2 res: ${task2(lines)}")

    val endTime = System.nanoTime()
    val elapsedTime = endTime - startTime
    val elapsedSeconds = elapsedTime / 1_000_000_000.0 // Convert nanoseconds to seconds

    println("Elapsed Time: $elapsedSeconds seconds")
}

fun task1(input: Array<String>): Int {
    val calibrationValues: MutableList<Int> = mutableListOf()
    input.forEach { line ->
        val first = findFirstNumber(line, rev = false, matchStrings = false)
        val second = findFirstNumber(line, rev = true, matchStrings = false)
        calibrationValues += (first + second).toInt()
    }
    return calibrationValues.reduce { acc, v -> acc + v }
}

fun task2(input: Array<String>): Int {
    val calibrationValues: MutableList<Int> = mutableListOf()
    input.forEach { line ->
        val first = findFirstNumber(line, rev = false, matchStrings = true)
        val second = findFirstNumber(line, rev = true, matchStrings = true)
        calibrationValues += (first + second).toInt()
    }
    return calibrationValues.reduce { acc, v -> acc + v }
}

fun findFirstNumber(input: String, rev: Boolean, matchStrings: Boolean): String {
    var delta = 1
    var index = 0
    var output = ""

    if (rev) {
        index = input.length - 1
        delta = -1
    }

    do {
        if (input[index].isDigit()) return input[index].toString()

        if (matchStrings) {
            for (i in numberStrings.indices) {
                val needle = numberStrings[i]
                if (index + needle.length <= input.length) {
                    val check = input.substring(index, index + needle.length)
                    if (check == needle) {
                        output = (i + 1).toString()
                        break
                    }
                }
            }
        }

        index += delta
    } while (output == "")

    return output
}
