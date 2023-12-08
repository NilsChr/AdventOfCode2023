package day7

import readLinesUsingBufferedReader

val cardValues: Array<Char> = "23456789TJQKA".toCharArray().toTypedArray()
fun main() {
    val startTime = System.nanoTime()
    println("Running day 7")
    val lines = readLinesUsingBufferedReader("day7/input")
    getCardRank("32T3K")
    println("Task 1 res: ")
    println("Task 2 res: ")
    val endTime = System.nanoTime()
    val elapsedTime = endTime - startTime
    val elapsedSeconds = elapsedTime / 1_000_000_000.0 // Convert nanoseconds to seconds
    println("Elapsed Time: $elapsedSeconds seconds")
}

fun getCardRank(card: String): Int {
    var rank: Int = 0

    var counts: MutableMap<Char, Int> = mutableMapOf()
    card.forEach {
        counts[it] = counts.getOrDefault(it, 0) + 1
    }

    // Convert map entries to a list of pairs and sort by counts in descending order
    val sortedCounts = counts.entries.toList().sortedByDescending { it.value }


// Print the sorted counts
    sortedCounts.forEach { (char, count) ->
        println("$char: $count")
    }

    if(sortedCounts[0].value == 5) return 1


    return rank
}