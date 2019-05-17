package nefedov.turing

import nefedov.turing.mashine.Shift
import nefedov.turing.mashine.State
import java.io.BufferedReader
import java.io.Reader
import java.text.ParseException
import java.util.regex.Pattern
import kotlin.streams.toList

class Parser private constructor(input: Reader) {
    private val dog = "@"
    private val top = "$"
    private val atPattern = Pattern.compile("(`[*+_]+`)|(`all`)")
    private val env = Pattern.compile("^env ([A-Z_]+)")
    private val endEnv = "!env"
    private val statePattern = Pattern.compile("^ *([A-Z_]+):")
    private val comment = Pattern.compile("^ *#")

    private val lines = BufferedReader(input).lines().toList()
    private var lineInd = 0

    private fun readPreprocessor(): Preprocessor {
        val values = Preprocessor.getDefaultValues()
        while (lines[lineInd].startsWith(dog)) {
            val line = lines[lineInd++]
            val (name, value) = line.substring(1).split(":")
            if (!values.contains(name)) {
                throw ParseException("No such preprocessor option: $name", lineInd)
            }
            if (value.isBlank()) {
                throw ParseException("Empty value", lineInd)
            }
            values[name] = value.trim()
        }
        return Preprocessor(values)
    }

    private fun parse(): Result {
        val states = mutableMapOf<String, State>()
        val preprocessor = readPreprocessor()

        var prefix = ""
        var state = State("")
        loop@ while (lineInd < lines.size) {
            val line = lines[lineInd++]
            when {
                line.isEmpty() || comment.matcher(line).find() -> continue@loop
                env.matcher(line).find() -> prefix = line.split(" ")[1] + "_"
                line == endEnv -> prefix = ""
                statePattern.matcher(line).find() -> {
                    val stateName = prefix + line.trim().split(":")[0]
                    state = states.getOrPut(stateName) { State(stateName) }
                }
                else -> {
                    // charAt operation [toState [putChar]]
                    val tokens = line.trim().split(" ")

                    if (tokens.size < 2 || tokens.size > 4) {
                        throw ParseException("Unexpected line", lineInd)
                    }

                    val atChars = tokens[0]

                    val operation = tokens[1]

                    val rawName = tokens.getOrNull(2)
                    val toStateName = when {
                        rawName == null || rawName == "." -> state.name
                        rawName.startsWith(top) -> rawName.substring(1)
                        else -> prefix + rawName
                    }
                    val toState = states.getOrPut(toStateName) { State(toStateName) }

                    when {
                        atChars == "`all`" -> with(preprocessor) { alphabet + special + blank }
                        atPattern.matcher(atChars).find() -> atChars.map {
                            when (it) {
                                '*' -> preprocessor.alphabet
                                '+' -> preprocessor.special
                                '_' -> preprocessor.blank
                                else -> ""
                            }
                        }.reduce(String::plus)
                        else -> atChars
                    }.forEach { atChar ->
                        val putChar = tokens.getOrElse(3) { "$atChar" }
                        state.shifts += Shift(Shift.getDirectionByName(operation), "$atChar", toState, putChar)
                    }
                }
            }
        }

        return Result(preprocessor, ArrayList(states.values))
    }

    data class Result(val preprocessor: Preprocessor, val states: List<State>)

    companion object {
        fun parse(input: Reader): Result {
            try {
                return Parser(input).parse()
            } catch (e: ParseException) {
                System.err.println("In line ${e.errorOffset}: ${e.message}")
                throw e
            }
        }
    }
}