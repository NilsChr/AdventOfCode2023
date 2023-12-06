package template

import readLinesUsingBufferedReader

fun main() {
    val startTime = System.nanoTime()
    println("Running day x")
    val lines = readLinesUsingBufferedReader("dayx/input")
    println("Task 1 res: ")
    println("Task 2 res: ")
    val endTime = System.nanoTime()
    val elapsedTime = endTime - startTime
    val elapsedSeconds = elapsedTime / 1_000_000_000.0 // Convert nanoseconds to seconds
    println("Elapsed Time: $elapsedSeconds seconds")
}
