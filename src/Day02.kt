fun main() {

    val colors = listOf("red", "green", "blue")
    val colorsWithMax = mapOf("red" to 12, "green" to 13, "blue" to 14)

    fun parseRound(roundInput: String) = colors.associateWith { color ->
        Regex("""(\d+)\s$color""").find(roundInput)?.groupValues?.get(1)?.toInt() ?: 0
    }

    fun parseGame(gameInput: String) = gameInput.split(";").map(::parseRound)

    fun parseGames(input: List<String>) =
        input.map { it.split(":") }.associate { it[0].split(" ")[1].toInt() to parseGame(it[1]) }

    fun part1(input: List<String>) = parseGames(input).filterValues { rounds ->
        rounds.all { counts -> colorsWithMax.all { (color, max) -> counts[color]!! <= max } }
    }.keys.sum()

    fun part2(input: List<String>) = parseGames(input).values.map { rounds ->
        colors.map { color -> rounds.maxOf { it[color]!! } }
    }.sumOf { it.fold(1) { p, n -> p * n }.toInt() }

    val input = readInput("Day02")
    val part1Answer = part1(input)
    val part2Answer = part2(input)
    println("part 1: $part1Answer")
    println("part 2: $part2Answer")
}
