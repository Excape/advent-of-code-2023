import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.sqrt


fun main() {

    fun parseInput(input: List<String>): List<Pair<Long, Long>> {
        val times = input[0].split("Time:")[1].trim().split("\\s+".toRegex()).map { it.toLong() }
        val records = input[1].split("Distance:")[1].trim().split("\\s+".toRegex()).map { it.toLong() }
        return times.zip(records)
    }

    fun getWaysToWin(time: Long, record: Long): Long {
        // 1/2 (t +- sqrt(-4 r + t^2)), thanks wolfram alpha
        val sqrt = sqrt(time.toDouble().pow(2) - record * 4)
        // adding some small offset to account for having to beat the record, not just equal it :))))
        val lowerBound = ceil((time - sqrt) / 2 + 0.000001).toLong()
        val upperBound = floor((time + sqrt) / 2 - 0.000001).toLong()
        return upperBound - lowerBound + 1
    }

    fun part1(input: List<String>): Long {
        val races = parseInput(input)
        val result = races.map { (time, record) ->
            getWaysToWin(time, record)
        }.reduce { a, b -> a * b }
        return result
    }

    fun part2(input: List<String>): Long {
        val races = parseInput(input)
        val oneRace = races.reduce { (oneTime, oneDistance), (time, distance) ->
            Pair(
                "$oneTime$time".toLong(),
                "$oneDistance$distance".toLong()
            )
        }
        println(oneRace)
        return getWaysToWin(oneRace.first, oneRace.second)
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288L)
    check(part2(testInput) == 71503L)


    val input = readInput("Day06")
    val part1Answer = part1(input)
    val part2Answer = part2(input)
    println("part 1: $part1Answer")
    println("part 2: $part2Answer")
}
