package day4

import readLinesUsingBufferedReader
import java.util.LinkedList
import java.util.Queue

fun main() {
    val startTime = System.nanoTime()
    val lines = readLinesUsingBufferedReader("day4/input")
    println("Running day 4")
    val tasks = task(lines)
    println("Task 1 res: ${tasks.first}")
    println("Task 2 res: ${tasks.second}")
    val endTime = System.nanoTime()
    val elapsedTime = endTime - startTime
    val elapsedSeconds = elapsedTime / 1_000_000_000.0 // Convert nanoseconds to seconds
    println("Elapsed Time: $elapsedSeconds seconds")
}

fun task(lines: Array<String>): Pair<Int, Int> {
    val cards = mutableMapOf<Int, Card>()
    val queue: Queue<Card> = LinkedList()
    var task1 = 0
    var task2 = 0

    lines.forEach{
        val card = parseLine(it)

        card.guesses.intersect(card.winning).forEachIndexed{ i, _ ->
            card.winningNumbers += card.id + i + 1
            if(card.score == 0) card.score = 1
            else card.score *= 2
        }

        task1 += card.score
        cards[card.id] = card
        queue += card
    }

    do {
        val current = queue.remove()
        current.winningNumbers.forEach{queue.add(cards[it])}
        task2++
    } while(!queue.isEmpty())

    return Pair(task1,task2)
}

class Card(val id: Int, val winning: Set<Int>, val guesses: Set<Int> ) {
    var score: Int = 0
    var winningNumbers: List<Int> = emptyList()
}

fun parseLine(line: String): Card {
    val numberRegex = Regex("\\d+")
    val numbers = numberRegex.findAll(line).map(MatchResult::value).map(String::toInt)
    return Card(numbers.first(), numbers.drop(1).take(10).toSet(), numbers.drop(11).toSet())
}