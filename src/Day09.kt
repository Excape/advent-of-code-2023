fun main() {

    fun findNext(seq: List<Long>, forwards: Boolean): Long {
        if (seq.all { it == 0L }) {
            return 0
        }
        val diffs = seq.zip(seq.slice(1..seq.lastIndex)).map { (a, b) -> b - a }
        val next = findNext(diffs, forwards)
        return if (forwards) next + seq.last() else seq.first() - next
    }

    fun parseSequences(input: List<String>) = input.map { it.split(" ").map { it.toLong() } }

    fun part1(input: List<String>) = parseSequences(input).sumOf { findNext(it, true) }

    fun part2(input: List<String>) = parseSequences(input).sumOf { findNext(it, false) }

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 114L)
    check(part2(testInput) == 2L)


    val input = readInput("Day09")
    val part1Answer = part1(input)
    val part2Answer = part2(input)
    println("part 1: $part1Answer")
    println("part 2: $part2Answer")
}
