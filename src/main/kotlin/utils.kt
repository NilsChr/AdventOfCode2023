

fun stringToIntArray(input: String): Array<Int> {
    return input.split(" ").filter{it.isNotEmpty()}.map { it.toInt() }.toTypedArray()
}
fun stringToLongArray(input: String): Array<Long> {
    return input.split(" ").filter{it.isNotEmpty()}.map { it.toLong() }.toTypedArray()
}

fun extractStrings(input: String): Array<String> {
    val numberRegex = Regex("\\b-?\\s+\\b")
    return numberRegex.findAll(input).map { it.value }.toList().toTypedArray()
}