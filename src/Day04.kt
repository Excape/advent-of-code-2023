import kotlin.math.pow

fun main() {
    fun parseInput(input: List<String>): List<Pair<Set<Int>, Set<Int>>> {
        return input.map { it.split(":")[1] }.map { it.split("|") }
            .map { card -> card.map { draw -> draw.trim().split("\\s+".toRegex()).map { it.toInt() } } }
            .map { Pair(it[0].toSet(), it[1].toSet()) }
    }

    fun scoreCard(card: Pair<Set<Int>, Set<Int>>) = 2.0.pow((card.first intersect card.second).size - 1).toInt()


    fun part1(input: List<String>): Int {
        val cards = parseInput(input)
        return cards.sumOf(::scoreCard)
    }

    fun part2(input: List<String>): Int {
        val cards = parseInput(input)
        val scores = cards.withIndex().associate { it.index to (it.value.first intersect it.value.second).size }
        val cardCounts = scores.keys.associateWith { 1 }.toMutableMap()
        cards.indices.forEach { i ->
            (i + 1..i + scores[i]!!).takeWhile { it in cardCounts }.forEach { iCopy ->
                cardCounts[iCopy] = cardCounts[iCopy]!! + cardCounts[i]!!
            }
        }
        return cardCounts.values.sum()
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)


    val input = readInput("Day04")
    val part1Answer = part1(input)
    val part2Answer = part2(input)
    println("part 1: $part1Answer")
    println("part 2: $part2Answer")
}
