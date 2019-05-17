package nefedov.turing

import java.io.FileReader
import java.io.FileWriter
import java.text.ParseException

fun main() {
    val task = mapOf("A" to "zero", "B" to "aplusb")
    val fileName = task["B"]
    val input = FileReader("$fileName.in")
    val output = FileWriter("$fileName.out")
    try {
        val (preprocessor, states) = Parser.parse(input)
        with(output) {
            write("start: ${preprocessor.start}\n")
            write("accept: ${preprocessor.accept}\n")
            write("reject: ${preprocessor.reject}\n")
            write("blank: ${preprocessor.blank}\n")

            states.forEach { write("$it") }

            flush()
        }
    } catch (e: ParseException) {
        System.err.println("Parse failed!")
    }
}