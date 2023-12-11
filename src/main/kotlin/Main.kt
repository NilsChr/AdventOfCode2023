
fun main(args: Array<String>) {
    val startTime = System.nanoTime()
    day11.main()
    val endTime = System.nanoTime()
    val elapsedTime = endTime - startTime
    val elapsedSeconds = elapsedTime / 1_000_000_000.0
    println("Elapsed Time: $elapsedSeconds seconds")
}