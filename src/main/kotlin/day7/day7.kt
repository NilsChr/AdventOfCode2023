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
    println("Running day 7")
    val startTime = System.nanoTime()
    val lines = readLinesUsingBufferedReader("day7/input")
    val cards = parseCards(lines)
    val task1 = part1(cards)
    cards.forEach { it.setJoker() }
    val task2 = part1(cards)
    val endTime = System.nanoTime()
    val elapsedTime = endTime - startTime
    val elapsedSeconds = elapsedTime / 1_000_000_000.0 // Convert nanoseconds to seconds
    println("Task 1 res: $task1")
    println("Task 2 res: $task2")
    println("Elapsed Time: $elapsedSeconds seconds")
}

fun parseCards(lines: Array<String>): List<Card> {
    val cards = mutableListOf<Card>()
    lines.forEach { line ->
        val parts = line.split(" ")
        cards.add(Card(parts[0], parts[1].toInt()))
    }
    return cards
}

fun part1(cards: List<Card>):Int {
    var score = 0
    val sorted = cards.sorted()
    sorted.forEachIndexed { index, card -> score += (index+1) * card.bid }
    return score
}

class Card(val hand: String, val bid: Int) : Comparable<Card> {
    private var score: CardScore = CardScore.HIGH_CARD
    private var joker: Boolean = false
    private var bestJokerHand: CardScore = CardScore.HIGH_CARD
    init {
        score = calculateScore(hand)
    }

    fun setJoker() {
        joker = true
        calculateBestJoker()
        score = bestJokerHand
    }

    private fun calculateBestJoker() {
        val values = "J23456789TQKA"
        for(element in values) {
            val tempScore = calculateScore(hand.replace('J', element))
            if(tempScore > bestJokerHand)
                bestJokerHand = tempScore
        }
    }

    private fun getCardValue(card: Char): Int {
        if(joker) return cardValuesJoker.indexOf(card)
        return cardValues.indexOf(card)
    }

    override fun compareTo(other: Card): Int {
        if (this.score != other.score) {
            return this.score.code - other.score.code
        }

        for (c in 0..<5) {
            val cardA = this.hand[c]
            val cardB = other.hand[c]
            val indexA = getCardValue(cardA)
            val indexB = getCardValue(cardB)
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