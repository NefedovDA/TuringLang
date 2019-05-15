package nefedov.turing

import java.io.BufferedReader
import java.io.BufferedWriter

class Parser {

    private fun BufferedWriter.putCommand(
        fromState: String,
        fromCh: String,
        operation: String,
        toState: String,
        putCh: String
    ) = write("$fromState $fromCh -> $toState $putCh $operation\n")

    fun parse(input: BufferedReader, output: BufferedWriter) {
        output.write(PREVIEW)

        val alphabet = input.readLine().map { "$it" }

        var prefix = ""
        input.readLines().forEach { line ->
            when {
                line.isEmpty() -> Unit // ignore
                line.startsWith("#") -> Unit // comment
                line.startsWith("fun") -> prefix = line.substring(4, line.length - 2) + "_" // start of function
                line.startsWith("}") -> prefix = "" // end of function
                else -> { // command
                    val tokens = line.split(' ')
                    val (fromState, fromCh, operation) = tokens
                    val toState = tokens.getOrElse(3) { fromState }
                    val chars = when (fromCh) {
                        "*" -> alphabet
                        "_*" -> alphabet + BLANK
                        else -> listOf(fromCh)
                    }
                    chars.forEach { ch ->
                        val putCh = tokens.getOrElse(4) { ch }
                        output.putCommand(
                            prefix + fromState,
                            ch,
                            operation,
                            if (toState.startsWith("$")) toState.substring(1) else prefix + toState,
                            putCh
                        )
                    }
                }
            }
        }

        output.flush()
    }

    companion object {
        private const val START = "S"
        private const val ACCEPT = "AC"
        private const val REJECT = "RJ"
        private const val BLANK = "_"

        private val PREVIEW =
            """
            start: $START
            accept: $ACCEPT
            reject: $REJECT
            blank: $BLANK

            """.trimIndent()
    }
}