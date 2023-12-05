package day5

import readLinesUsingBufferedReader
import stringToLongArray

fun main() {
    val startTime = System.nanoTime()
    println("Running day 5")
    val lines = readLinesUsingBufferedReader("day5/input")
    val schematic = parseInput(lines)
    schematic.calculateLocations()
    val task1 = schematic.locations.minOrNull()

    var i = 0
    var minLocation: Long = Long.MAX_VALUE
    do {
        val start = schematic.seeds[i]
        val end = schematic.seeds[i+1]
        i += 2
        for(j in 0 ..< end) {
            val tempLoc = schematic.getLocation(start+j)
            if(tempLoc < minLocation) minLocation = tempLoc
        }
    } while (i < schematic.seeds.size)

    val task2 = minLocation

    println("Task 1 res: $task1")
    println("Task 2 res: $task2")
    val endTime = System.nanoTime()
    val elapsedTime = endTime - startTime
    val elapsedSeconds = elapsedTime / 1_000_000_000.0 // Convert nanoseconds to seconds
    println("Elapsed Time: $elapsedSeconds seconds")
}

class Schematic(val seeds: Array<Long>, val mapTables: Array<MapTable>) {

    var locations: List<Long> = emptyList()

    fun calculateLocations() {
        seeds.forEach { seed ->
            var location:Long = seed
            mapTables.forEach { location = it.convert(location) }
            locations += location
        }
    }

    fun getLocation(input:Long): Long {
        var location = input
        mapTables.forEach { location = it.convert(location) }
        return location
    }
}

data class MapRange(val source:Long, val destination:Long, val range: Long){}

class MapTable() {
    var ranges: List<MapRange> = mutableListOf()

    fun convert(input: Long):Long {
        ranges.forEach{
            if (input >= it.source && input < it.source+it.range) {
                val rest = input - it.source
                return it.destination + rest
            }
        }
        return input
    }
}

fun parseInput(lines: Array<String>): Schematic {
    var seeds: Array<Long> = emptyArray()
    val mappingTables: MutableList<MapTable> = mutableListOf()
    var mapRanges: MutableList<MapRange> = mutableListOf()
    lines.forEachIndexed { index, s ->
        if(index == 0) {
            val sanitized = s.split(':')
            seeds = stringToLongArray(sanitized[1])
            return@forEachIndexed
        }
        if(s.contains('-')) {
            if(mapRanges.size > 0) {
                val table = MapTable()
                table.ranges = mapRanges
                mappingTables += table
            }
            mapRanges = mutableListOf()
            return@forEachIndexed
        }
        val numbers = stringToLongArray(s)
        if(numbers.isNotEmpty()) {
            mapRanges += MapRange(numbers[1], numbers[0], numbers[2])
        }
    }
    val table = MapTable()
    table.ranges = mapRanges
    mappingTables += table

    return Schematic(seeds, mappingTables.toTypedArray())
}