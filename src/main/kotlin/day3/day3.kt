package day3

import readFileToGrid

val gears = mutableMapOf<Pair<Int, Int>, List<Int>>()

fun main() {
    val startTime = System.nanoTime()
    println("Running day3")
    val grid = readFileToGrid("day3/input")
    val res = task(grid)
    println("Task 1 res: ${res.first}")
    println("Task 2 res: ${res.second}")
    val endTime = System.nanoTime()
    val elapsedTime = endTime - startTime
    val elapsedSeconds = elapsedTime / 1_000_000_000.0 // Convert nanoseconds to seconds
    println("Elapsed Time: $elapsedSeconds seconds")
}

class Part(private val x:Int, private val y: Int, val value: Int, private val length: Int, var isInvalid: Boolean = true) {

    fun validate(grid :Array<Array<Char>>) {
        for(y in this.y-1..< this.y +2) {
            for(x in this.x-1..< this.x + this.length +1) {
                if(y < 0 ||x < 0 || y > grid.size-1 || x > grid[y].size-1) continue
                val neighbour = grid[y][x]
                if(neighbour.isDigit()) continue
                if(neighbour != '.')  {
                    this.isInvalid = false
                    if(neighbour == '*') {
                        if(gears[Pair(y,x)] == null) {
                            gears[Pair(y,x)] = emptyList()
                        }
                        gears[Pair(y,x)] = gears[Pair(y,x)]!! + this.value
                    }
                }
            }
        }
    }
}

fun task(grid :Array<Array<Char>>): Pair<Int, Int> {
    val validParts: MutableList<Part> = emptyList<Part>().toMutableList()
    for(y in grid.indices) {
        var x = 0
        do {
            val current = grid[y][x]
            if(current == '.') continue
            var peek = x
            val start = x
            if(current.isDigit()) {
                val numbers: MutableList<Char> = emptyList<Char>().toMutableList().toMutableList()
                do {
                    numbers += grid[y][peek]

                    peek++
                    if(peek > grid[y].size-1) break

                } while (grid[y][peek].isDigit())
                val value = numbers.joinToString("").toInt()
                validParts += Part(start, y,value, numbers.size )
            }
            x = peek
        } while(x++ < grid[y].size-1)
    }

    validParts.forEach{it.validate(grid)}

    var task1 = 0
    validParts.filter { !it.isInvalid }.forEach{
        task1 += it.value
    }

    var task2 = 0
    for ((_, value) in gears) {
        if(value.size == 2) {
            task2 += value[0] * value[1]
        }
    }

    return Pair(task1, task2)
}