package day8

import readLinesUsingBufferedReader

var instructions: String = ""
var nodes = mutableMapOf<String, Node>()

fun main() {
    val startTime = System.nanoTime()
    println("Running day 8")
    val lines = readLinesUsingBufferedReader("day8/input")
    parseNodes(lines)
    println("Task 1 res: ${part1()}")
    println("Task 2 res: ${part2()}")
    val endTime = System.nanoTime()
    val elapsedTime = endTime - startTime
    val elapsedSeconds = elapsedTime / 1_000_000_000.0 // Convert nanoseconds to seconds
    println("Elapsed Time: $elapsedSeconds seconds")
}

class Node(val value:String, val left: String, val right: String) {}

fun parseNodes(lines: Array<String>) {
    instructions = lines[0]
    for(i in 2..<lines.size) {
        val values = lines[i].replace("=",",").replace("(","").replace(")","").trim().split(",") //extractStrings(lines[i])
        nodes[values[0].trim()] = Node(values[0].trim(), values[1].trim(), values[2].trim())
    }
}

fun part1():Long {
    return traverse(nodes, "AAA", instructions)
}

fun part2(): Long {
    val scores: MutableList<Long> = mutableListOf()
    nodes.forEach { (key, value) ->
        if (value.value[2] == 'A') {
            scores += traverse(nodes, key, instructions)
        }
    }
    return scores.reduce { acc, i -> lcd(acc, i) }
}

fun traverse(nodes: MutableMap<String, Node>,current: String, instructions: String): Long {
    var steps = 0L
    var nextId = current
    while(true) {
        val node = nodes[nextId]
        if (node != null && node.value[2] == 'Z') break
        val nextIns = instructions[(steps % instructions.length).toInt()]
        if(nextIns == 'L') {
            nextId = node?.left.toString()
        } else if(nextIns == 'R') {
            nextId = node?.right.toString()
        }
        steps++
    }
    return steps
}
fun lcd(a: Long, b: Long): Long {
    return a * b / gcd(a, b)
}
fun gcd(a: Long, b: Long): Long {
    return if (b == 0L) a else gcd(b, a % b)
}
