import java.io.File

suspend fun main() {
    print("Day? >> ")
    val day = readLine()?.toInt() ?: throw IllegalStateException("Provide day input")
    fetchInput(day)

    val ktFile = File("src", "Day${paddedDay(day)}.kt")
    ktFile.writeText(getTemplate(day))
}

fun getTemplate(day: Int): String = """

fun main() {
    fun part1(input: List<String>): Int {
        return 0
    }
    
    fun part2(input: List<String>): Int {
        return 0
    }

//    val testInput = readInput("Day${paddedDay(day)}_test")
//    check(part1(testInput) == 0)
//    check(part2(testInput) == 0)


    val input = readInput("Day${paddedDay(day)}")
    val part1Answer = part1(input)
    val part2Answer = part2(input)
    println("part 1: ${'$'}part1Answer")
    println("part 2: ${'$'}part2Answer")

//    submit($day, 1, part1Answer)
//    submit($day, 2, part2Answer)
}
"""
