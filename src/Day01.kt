fun main() {
    fun part1(input: List<String>): Int {
        return input.map { it.filter(Char::isDigit) }.sumOf { it.first().digitToInt() * 10 + it.last().digitToInt() }
    }

    fun part2(input: List<String>): Int {
        val digitsMap = mapOf(
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9,
        ) + (1..9).associateBy { it.toString() }
        return input.sumOf { line ->
            val first =
                digitsMap.map { it.value to line.indexOf(it.key) }.filter { it.second >= 0 }.minBy { it.second }.first
            val last = digitsMap.maxBy { line.lastIndexOf(it.key) }.value
            first * 10 + last
        }

    }

    val testInput = readInput("Day01_test")
    check(part2(testInput) == 281)


    val input = readInput("Day01")
    val part1Answer = part1(input)
    val part2Answer = part2(input)
    println("part 1: $part1Answer")
    println("part 2: $part2Answer")

}
