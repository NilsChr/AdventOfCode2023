
fun stringToIntArray(input: String): Array<Int> {
    val numberRegex = Regex("\\b-?\\d+\\b")
    return numberRegex.findAll(input).map { it.value.toInt() }.toList().toTypedArray()
}
fun stringToIntArray2(input: String): Array<Int> {
    return input.split("\\s+".toRegex()).map { it.toInt() }.toTypedArray()
}
fun stringToIntArrayFast(input: String): Array<Int> {
    return input.split(" ").map { it.toInt() }.toTypedArray()
}
fun stringToLongArray(input: String): Array<Long> {
    val numberRegex = Regex("\\b-?\\d+\\b")
    return numberRegex.findAll(input).map { it.value.toLong() }.toList().toTypedArray()
}

fun extractStrings(input: String): Array<String> {
    val numberRegex = Regex("\\b-?\\s+\\b")
    return numberRegex.findAll(input).map { it.value }.toList().toTypedArray()
}