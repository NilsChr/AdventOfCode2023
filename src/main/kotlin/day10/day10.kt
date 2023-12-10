package day10

import readFileToGrid
import java.awt.geom.Path2D
import java.awt.geom.Point2D

class Pipe(private val up: CharArray,private val down: CharArray,private val left: CharArray,private val right: CharArray) {
    fun getNeighbours(grid: Array<Array<Char>>, from: Point2D): List<Point2D> {
        val neighbours = mutableListOf<Point2D?>()
        neighbours.add(checkDirection(grid, from, up, Point2D.Double(0.0,-1.0)))
        neighbours.add(checkDirection(grid, from, down, Point2D.Double(0.0,1.0)))
        neighbours.add(checkDirection(grid, from, left, Point2D.Double(-1.0,0.0)))
        neighbours.add(checkDirection(grid, from, right, Point2D.Double(1.0,0.0)))
        return neighbours.filterNotNull()
    }
    private fun checkDirection(grid: Array<Array<Char>>, from: Point2D, match: CharArray, dir: Point2D): Point2D? {
        val check = Point2D.Double(from.x+dir.x, from.y+dir.y)
        if (check.y < 0 || check.y > grid.size-1) return null
        if (check.x < 0 || check.x > grid[0].size-1) return null
        if(match.contains(grid[check.y.toInt()][check.x.toInt()])) return check
        return null
    }
}

val pipes = mutableMapOf<Char, Pipe>()

fun main() {
    val startTime = System.nanoTime()
    println("Running day 10")
    val lines = readFileToGrid("day10/input")
    initPipes()
    val polygon = createPolygon(lines)
    println("Task 1 res: ${polygon.size/2}")
    println("Task 2 res: ${part2(lines, polygon)}")
    val endTime = System.nanoTime()
    val elapsedTime = endTime - startTime
    val elapsedSeconds = elapsedTime / 1_000_000_000.0 // Convert nanoseconds to seconds
    println("Elapsed Time: $elapsedSeconds seconds")
}

fun part2(grid: Array<Array<Char>>, polygon: List<Point2D>): Int {
    var score = 0
    val path = Path2D.Double()
    path.moveTo(polygon[0].x, polygon[0].y)
    for (i in 1..<polygon.size) {
        path.lineTo(polygon[i].x, polygon[i].y)
    }
    path.closePath()

    grid.forEachIndexed { y, line ->
        line.forEachIndexed { x, char ->
            val p = Point2D.Double(x.toDouble(),y.toDouble())
            if(!polygon.contains(p) && path.contains(p)) score++
        }
    }
    return score
}

fun initPipes() {
    pipes['S'] = Pipe( "S|7F".toCharArray(), "S|JL".toCharArray(), "S-LF".toCharArray(), "S-J7".toCharArray() )
    pipes['|'] = Pipe( "S|7F".toCharArray(), "S|JL".toCharArray(), "".toCharArray(), "".toCharArray() )
    pipes['-'] = Pipe( "".toCharArray(), "".toCharArray(), "S-LF".toCharArray(), "S-J7".toCharArray()  )
    pipes['L'] = Pipe( "S|7F".toCharArray(), "".toCharArray(), "".toCharArray(), "S-J7".toCharArray()  )
    pipes['J'] = Pipe( "S|7F".toCharArray(), "".toCharArray(), "S-LF".toCharArray(), "".toCharArray() )
    pipes['7'] = Pipe( "".toCharArray(), "S|JL".toCharArray(), "S-LF".toCharArray(), "".toCharArray() )
    pipes['F'] = Pipe( "".toCharArray(), "S|JL".toCharArray(), "".toCharArray(), "S-J7".toCharArray() )
}

fun createPolygon(grid: Array<Array<Char>>): List<Point2D> {
    val start = findStart(grid)
    return traverse(grid, start)
}

fun findStart(grid: Array<Array<Char>>): Point2D {
    val cords = Point2D.Double(0.0,0.0)
    grid.forEachIndexed { y, line ->
        line.forEachIndexed { x, char ->
            if (char == 'S') {
                cords.x = x.toDouble()
                cords.y = y.toDouble()
                return cords
            }
        }
    }
    return cords
}

fun traverse(grid: Array<Array<Char>>, start: Point2D): MutableList<Point2D> {
    val visited = mutableListOf<Point2D>()
    visited.add(start)
    var neighbours = getNeighbours(grid, start)
    println("Start neighbours: ${neighbours.size}")
    var next: Point2D? = neighbours[0]
    do {
        if (next == null) break
        visited.add(next)
        neighbours = getNeighbours(grid, next)
        next = null
        neighbours.forEach {
            if (visited.contains(it)) return@forEach
            next = it
        }

    } while (next != null)
    return visited
}

fun getNeighbours(grid: Array<Array<Char>>, pos: Point2D): List<Point2D> {
    val from = grid[pos.y.toInt()][pos.x.toInt()]
    val fromPipe = pipes[from] ?: return listOf()
    return fromPipe.getNeighbours(grid, pos)
}