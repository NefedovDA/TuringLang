package nefedov.turing

import nefedov.turing.mashine.State
import java.io.BufferedReader
import java.io.Reader

class Parser private constructor(input: Reader) {
    private val input = BufferedReader(input)

    private fun readPreprocessor(): Preprocessor {
        input.readLine()
        val values = Preprocessor.getDefaultValues()
        while (true) {
            val line = input.readLine()
            when (line) {
                "}" -> return Preprocessor(values)
                else -> {
                    val (name, tail) = line.split(':')
                    values[name] = tail.replace(" ", "")
                }
            }
        }
    }

    fun parse(): Result {
        val states = mutableMapOf<String, State>()
        val preprocessor = readPreprocessor()
        val alphabet = preprocessor.alphabet.map { "$it" }
        val special = preprocessor.special.map { "$it" }
        val blanks = preprocessor.blank.map { "$it" }

        return Result(preprocessor, states)
    }

    data class Result(val preprocessor: Preprocessor, val states: Map<String, State>)
}