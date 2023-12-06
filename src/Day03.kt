fun main() {

    fun parseInput(input: List<String>): Map<Point, Char> {
        return input.mapIndexed { y, line -> line.withIndex().associate { (index, value) -> Point(index, y) to value } }
            .flatMap { it.entries }.associate { it.key to it.value }
    }

    val directions =
        listOf(
            Point(-1, 0),
            Point(0, -1),
            Point(1, 0),
            Point(0, 1),
            Point(-1, -1),
            Point(1, 1),
            Point(1, -1),
            Point(-1, 1)
        )

    fun findWholeNumber(schema: Map<Point, Char>, contactPoint: Point): Pair<Point, Int> {
        var wholeNumber = schema[contactPoint].toString()
        var leftPoint = contactPoint
        (1..2).map { Point(contactPoint.x - it, contactPoint.y) }.map { it to schema[it] }
            .takeWhile { (p, c) -> c?.isDigit() == true }
            .forEach { (p, c) ->
                wholeNumber = "$c$wholeNumber"
                leftPoint = p
            }
        (1..2).map { schema[Point(contactPoint.x + it, contactPoint.y)] }
            .takeWhile { it?.isDigit() == true }
            .forEach { wholeNumber = "$wholeNumber$it" }
        return leftPoint to wholeNumber.toInt()
    }

    fun findAdjacentNumbers(
        p: Point,
        schema: Map<Point, Char>
    ) = directions.map { dir -> Point(p.x + dir.x, p.y + dir.y) }
        .associateWith { schema[it] }
        .filterValues { it?.isDigit() == true }.keys

    fun part1(input: List<String>): Int {
        val schema = parseInput(input)
        val schemaNumbers = mutableMapOf<Point, Int>()

        schema.filterValues { !it.isDigit() && it != '.' }.forEach { (key, value) ->
            val matches = findAdjacentNumbers(key, schema)
            matches.map { findWholeNumber(schema, it) }.forEach { (p, number) ->
                schemaNumbers[p] = number
            }
        }
        return schemaNumbers.values.sum()
    }

    fun part2(input: List<String>): Int {
        val schema = parseInput(input)

        return schema.filterValues { it == '*' }.keys.map { p -> findAdjacentNumbers(p, schema) }
            .map { matches -> matches.map { findWholeNumber(schema, it) }.toSet() }.filter { it.size == 2 }
            .sumOf { numbers -> numbers.map { it.second }.reduce { p, n -> n * p } }
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)


    val input = readInput("Day03")
    val part1Answer = part1(input)
    val part2Answer = part2(input)
    println("part 1: $part1Answer")
    println("part 2: $part2Answer")
}
