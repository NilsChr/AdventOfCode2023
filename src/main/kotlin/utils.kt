
fun stringToIntArray(input: String): Array<Int> {
    val numberRegex = Regex("\\b\\d+\\b")
    return numberRegex.findAll(input).map { it.value.toInt() }.toList().toTypedArray()
}

fun stringToLongArray(input: String): Array<Long> {
    val numberRegex = Regex("\\b\\d+\\b")
    return numberRegex.findAll(input).map { it.value.toLong() }.toList().toTypedArray()
}