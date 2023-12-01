import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File( "src/inputs","$name.txt")
    .readLines()

fun readInputAsString(name: String) = File( "src/inputs","$name.txt").readText()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')


fun<T> submit(day: Int, level: Int, answer: T) {
    val session = readSession()
    val client = HttpClient()
    runBlocking {
        val response: HttpResponse = client.submitForm(
            url = "https://adventofcode.com/2022/day/$day/answer",
            formParameters = Parameters.build {
                append("level", level.toString())
                append("answer", answer.toString())
            }
        ) {
            cookie("session", session)
        }
        if (response.status != HttpStatusCode.OK) {
            println("Unsuccessful: $response")
        } else {
            println("Success!")
        }
    }

}

suspend fun fetchInput(day: Int) {
    val client = HttpClient()
    val session = readSession()
    val response: HttpResponse = client.request("https://adventofcode.com/2023/day/${day}/input") {
        cookie("session", session)
    }
    if (response.status != HttpStatusCode.OK) {
        throw IllegalStateException("HTTP call unsuccessful")
    }
    val body: ByteArray = response.body()

    val inputFile = File("src/inputs/", "Day${paddedDay(day)}.txt")
    inputFile.writeBytes(body)

    File("src/inputs/", "Day${paddedDay(day)}_test.txt").createNewFile()
}

fun paddedDay(day: Int) = day.toString().padStart(2, '0')

private fun readSession() = File("session").readText()

data class Point(val x: Int, val y: Int)