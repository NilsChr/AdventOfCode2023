package day11

import readFileToGrid
import java.awt.Point
import kotlin.concurrent.thread
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.max
data class Galaxy(val id: Int, val position: Point)

fun main() {
    val lines = readFileToGrid("day11/input")
    val galaxies = locateGalaxies(lines)

    val task1Thread = thread {
        val task1 = process(galaxies, expandUniverse(lines, 2))
        println("✅Task 1 res: $task1")
    }

    val task2Thread = thread {
        val task2 = process(galaxies, expandUniverse(lines, 1000000))
        println("✅Task 2 res: $task2")
    }

    task1Thread.join()
    task2Thread.join()
}
fun expandUniverse(universe: Array<Array<Char>>, rate: Int): Pair<Array<Int>, Array<Int>> {
    val spaceY = Array(universe.size) { i ->
        if (universe[i].all { it == '.' }) rate else 1
    }

    val spaceX = Array(universe[0].size) { scanX ->
        if (universe.any { it[scanX] == '#' }) 1 else rate
    }

    return Pair(spaceX, spaceY)
}

fun locateGalaxies(universe: Array<Array<Char>>): MutableList<Galaxy> {
    val galaxies = mutableListOf<Galaxy>()
    var id = 1
    for(y in universe.indices) {
        for(x in 0 ..< universe[y].size) {
            if(universe[y][x] == '#') {
                galaxies.add(Galaxy(id++,Point(x,y)))
            }
        }
    }
    return galaxies
}

fun process(galaxies: MutableList<Galaxy>,expansions: Pair<Array<Int>, Array<Int>> ): Long {
    val shortPaths = mutableMapOf<String, Long>()
    for(galaxy in galaxies) {
        for(other in galaxies.filter { it != galaxy }) {
            val startX = min(galaxy.position.x, other.position.x)
            val distX  = abs(galaxy.position.x - other.position.x)
            var travelX = 0L
            for(x in startX ..< startX+distX) {
                travelX += expansions.first[x]
            }

            val startY = min(galaxy.position.y, other.position.y)
            val distY  = abs(galaxy.position.y - other.position.y)
            var travelY = 0L
            for(y in startY ..< startY+distY) {
                travelY += expansions.second[y]
            }

            val dist = travelX + travelY
            val key = min(galaxy.id, other.id).toString() + "_" + max(galaxy.id, other.id).toString()
            if(shortPaths[key] == null) {
                shortPaths[key] = dist
            } else if (dist < shortPaths[key]!!) {
                shortPaths[key] = dist
            }
        }
    }
    var score = 0L
    shortPaths.values.forEach{score += it}
    return score
}