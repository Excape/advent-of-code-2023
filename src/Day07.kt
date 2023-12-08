fun main() {

    val faceCardValues = mapOf(
        'T' to 10,
        'J' to 11,
        'Q' to 12,
        'K' to 13,
        'A' to 14
    )

    fun getCardStrength(card: Char, withJokerRule: Boolean) = when {
        card.isDigit() -> card.digitToInt()
        withJokerRule && card == 'J' -> 1
        else -> faceCardValues[card]!!
    }

    fun getFrequencies(handInput: String, withJokerRule: Boolean): Map<Char, Int> {
        val frequencies = handInput.groupingBy { it }.eachCount()
        return when {
            !withJokerRule || 'J' !in frequencies -> frequencies
            frequencies.size == 1 -> frequencies // all jokers
            else -> frequencies.toMutableMap().apply {
                val mostFrequentNonJoker = filterKeys { it != 'J' }.maxBy { it.value }
                this[mostFrequentNonJoker.key] = mostFrequentNonJoker.value + this['J']!!
                remove('J')
            }
        }

    }

    class Hand(val input: String, val withJokerRule: Boolean = false) : Comparable<Hand> {
        val frequencies = getFrequencies(input, withJokerRule)
        val maxFrequency = frequencies.values.max()

        val handValue = when (frequencies.size) {
            1 -> 7 // FourOAK
            2 -> when (maxFrequency) {
                4 -> 6 // FiveOAK
                else -> 5 // FH
            }

            3 -> when (maxFrequency) {
                3 -> 4 // TOAK
                else -> 3 // Two pair
            }

            4 -> 2 // one pair
            else -> 1 // high card
        }

        fun compareByStrength(thisInput: String, otherInput: String): Int {
            val thisFirstCard = getCardStrength(thisInput[0], withJokerRule)
            val otherFirstCard = getCardStrength(otherInput[0], withJokerRule)
            return when (val comp = thisFirstCard.compareTo(otherFirstCard)) {
                0 -> compareByStrength(thisInput.substring(1), otherInput.substring(1))
                else -> comp
            }
        }

        override fun compareTo(other: Hand) = when (val valueComp = handValue.compareTo(other.handValue)) {
            0 -> compareByStrength(this.input, other.input)
            else -> valueComp
        }

        override fun toString() = input
    }

    fun part1(input: List<String>) = input.map { it.split(" ") }
        .map { Pair(Hand(it[0]), it[1].toInt()) }
        .sortedBy { it.first }
        .mapIndexed { rank, (hand, bid) -> (rank + 1) * bid }
        .sum()

    fun part2(input: List<String>) = input.map { it.split(" ") }
        .map { Pair(Hand(it[0], true), it[1].toInt()) }
        .sortedBy { it.first }
        .mapIndexed { rank, (hand, bid) -> (rank + 1) * bid }
        .sum()

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440)
    check(part2(testInput) == 5905)


    val input = readInput("Day07")
    val part1Answer = part1(input)
    val part2Answer = part2(input)
    println("part 1: $part1Answer")
    println("part 2: $part2Answer")
}
