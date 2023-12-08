fun main() {
    // repeat a sequence indefinitely
    fun <T> Sequence<T>.repeat() = sequence { while (true) yieldAll(this@repeat) }

    fun parseInput(input: List<String>): Pair<String, Map<String, Pair<String, String>>> {
        val instructions = input[0]
        val graph = input.drop(2).map { it.split(" = ") }
            .map {
                it[0] to it[1].removeSurrounding("(", ")").split(", ")
            }
            .associate { (k, v) -> k to (v[0] to v[1]) }
        return Pair(instructions, graph)
    }

    fun part1(input: List<String>): Int {
        val (instructions, graph) = parseInput(input)
        var current = "AAA"
        val stepCount = instructions.asSequence().repeat().map { instr ->
            with(graph[current]!!) {
                when(instr) {
                    'L' -> this.first
                    'R' -> this.second
                    else -> throw IllegalArgumentException("invalid instruction $instr")
                }
            }.also { current = it }
        }.takeWhile { it != "ZZZ" }.count()
        return stepCount + 1
    }

    fun part2(input: List<String>): Long {
        val (instructions, graph) = parseInput(input)

        val stepCounts = graph.keys.filter {it.endsWith('A')}.map { startNode ->
            var current = startNode
            instructions.asSequence().repeat().map { instr ->
                with(graph[current]!!) {
                    when(instr) {
                        'L' -> this.first
                        'R' -> this.second
                        else -> throw IllegalArgumentException("invalid instruction $instr")
                    }
                }.also { current = it }
            }.takeWhile { !it.endsWith("Z") }.count() + 1L
        }
        println(stepCounts)
        return stepCounts.lcm()
    }

    val testInput = readInput("Day08_test")
    check(part2(testInput) == 6L)

    val input = readInput("Day08")
    val part1Answer = part1(input)
    val part2Answer = part2(input)
    println("part 1: $part1Answer")
    println("part 2: $part2Answer")
}
