package day4

import readFile
import java.util.LinkedList
import java.util.Queue

fun main() {
    val startTime = System.nanoTime()
    println("Running day x")
    val lines = readFile("day4/input")
    val tasks = task(lines)
    println("Task 1 res: ${tasks.first}")
    println("Task 2 res: ${tasks.second}")
    val endTime = System.nanoTime()
    val elapsedTime = endTime - startTime
    val elapsedSeconds = elapsedTime / 1_000_000_000.0 // Convert nanoseconds to seconds
    println("Elapsed Time: $elapsedSeconds seconds")
}

fun task(lines: List<String>): Pair<Int, Int> {
    val cards = mutableMapOf<Int, Card>()
    val queue: Queue<Card> = LinkedList()
    var task1 = 0

    lines.forEach{
        val card = parseLine(it)
        card.process()
        task1 += card.score
        cards[card.id] = card
        queue += card
    }

    var task2 = 0

    do {
        val current = queue.remove()
        current.winningNumbers.forEach{queue.add(cards[it])}
        task2++
    } while(!queue.isEmpty())

    return Pair(task1,task2)
}

class Card(val id: Int, val winning: List<Int>, val guesses: List<Int> ) {
    var score: Int = 0
    var winningNumbers: List<Int> = emptyList()
    fun process() {
        var wins: List<Int> = emptyList()
        this.guesses.forEach{ guess ->
            if(this.winning.contains(guess)) {
                wins += guess
                if(score == 0) score = 1
                else score *= 2
            }
        }
        wins.forEachIndexed{i,_ ->
            winningNumbers += id + i + 1
        }
    }
    override fun toString(): String {
        return "Card $id: ${winning.joinToString()} | ${guesses.joinToString()}"
    }
}

fun parseLine(line: String): Card {
    val partsA = line.split(':')
    val idParts = partsA[0].split(' ')
    val id = idParts[idParts.size-1].toInt()
    val numbers = partsA[1].split('|')
    val winning = numbers[0].trim().split(" ").filter{it.isNotEmpty()}.map{it.toInt()}
    val guesses = numbers[1].trim().split(" ").filter{it.isNotEmpty()}.map{it.toInt()}
    return Card(id, winning, guesses)
}