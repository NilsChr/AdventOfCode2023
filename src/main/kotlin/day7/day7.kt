package day7

import readLinesUsingBufferedReader

val cardValues: Array<Char> = "23456789TJQKA".toCharArray().toTypedArray()
val cardValuesJoker: Array<Char> = "J23456789TQKA".toCharArray().toTypedArray()

enum class CardScore(val code: Int) {
    HIGH_CARD(1),
    ONE_PAIR(2),
    TWO_PAIR(3),
    SAME_3(4),
    FULL_HOUSE(5),
    SAME_4(6),
    SAME_5(7)
}
fun main() {
    val startTime = System.nanoTime()
    println("Running day 7")
    val lines = readLinesUsingBufferedReader("day7/input")
    val cards = parseCards(lines, cardValues, false)
    val jokers = parseCards(lines, cardValuesJoker, true)
    println("Task 1 res: ${part1(cards)}")
    println("Task 2 res: ${part1(jokers)}")
    val endTime = System.nanoTime()
    val elapsedTime = endTime - startTime
    val elapsedSeconds = elapsedTime / 1_000_000_000.0 // Convert nanoseconds to seconds
    println("Elapsed Time: $elapsedSeconds seconds")
}

fun parseCards(lines: Array<String>, cardValues: Array<Char>, joker: Boolean): List<Card> {
    val cards = mutableListOf<Card>()
    lines.forEach { line ->
        val parts = line.split(" ")
        cards.add(Card(parts[0], parts[1].toInt(), cardValues, joker))
    }
    return cards
}

fun part1(cards: List<Card>):Int {
    var score = 0
    val sorted = cards.sorted()
    sorted.forEachIndexed { index, card -> score += (index+1) * card.bid }
    return score
}

class Card(val hand: String, val bid: Int, val cValues: Array<Char>, val joker: Boolean) : Comparable<Card> {
    var score: CardScore = CardScore.HIGH_CARD
    private var bestJokerHand: CardScore = CardScore.HIGH_CARD
    init {
        score = calculateScore(hand)
        calculateBestJoker()
        if(joker) score = bestJokerHand
    }

    private fun calculateBestJoker() {
        val values = "J23456789TQKA"
        for(i in 0 until values.length) {
            val tempScore = calculateScore(hand.replace('J', values[i]))
            if(tempScore > bestJokerHand)
                bestJokerHand = tempScore
        }
    }

    override fun compareTo(other: Card): Int {
        if (this.score != other.score) {
            return this.score.code - other.score.code
        }

        for (c in 0 until 5) {
            val cardA = this.hand[c]
            val cardB = other.hand[c]
            val indexA = cValues.indexOf(cardA)
            val indexB = cValues.indexOf(cardB)

            if (indexA != indexB) {
                return indexA - indexB
            }
        }
        return 0
    }

    override fun toString(): String {
        return "$hand -> ${score.name}"
    }
}

fun calculateScore(hand: String): CardScore {
    val charCounts = mutableMapOf<Char, Int>()
    for (char in hand) {
        charCounts[char] = charCounts.getOrDefault(char, 0) + 1
    }
    var foundThree = false
    var foundTwo = false
    for ((_, count) in charCounts) {
        if (count == 5) return CardScore.SAME_5
        if (count == 4) return CardScore.SAME_4
        if (count == 3) foundThree = true
        if (count == 2) foundTwo = true
    }
    if(foundThree && foundTwo) {
        return CardScore.FULL_HOUSE

    }
    if(foundThree) {
        return CardScore.SAME_3
    }
    if(charCounts.filter { it.value == 2 }.keys.size == 2) {
        return CardScore.TWO_PAIR
    }
    if(charCounts.filter { it.value == 2 }.keys.size == 1) {
        return CardScore.ONE_PAIR
    }
    return CardScore.HIGH_CARD
}