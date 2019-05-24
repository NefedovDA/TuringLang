package nefedov.turing

import nefedov.turing.mashine.Description
import nefedov.turing.mashine.State
import java.io.BufferedReader
import java.io.IOException
import java.io.Reader
import java.text.ParseException

object Parser {
    private object Patterns {
        private const val SYM = "[^\\s`]"
        private const val S = "(\\s+)"

        val NAME = Regex("([A-Za-z][A-Za-z0-1_]*)")
        val SPACES = Regex(S)
        val STATE_NAME = Regex("^\\s*$NAME:")
        val COMMENT = Regex("^\\s*(--|-\\|).*")
        val SHIFT = Regex("^$S?($SYM+|`$SYM+`)+$S[<^>]($S(\\$?$NAME|\\.)($S$SYM)?)?")
        val ENV_START = Regex("env$S$NAME")
        val ENV_END = Regex("^!env")

        object Directive {
            val GENERIC = Regex("^@.*:$S.*")
            val START = Regex("^@(start|s):$S$NAME")
            val ACCEPT = Regex("^@(accept|ac):$S$NAME")
            val REJECT = Regex("^@(reject|rj):$S$NAME")
            val BLANK = Regex("^@(blank|_):$S$SYM")
            val USER = Regex("^@:$S$SYM$S$SYM+")
        }
    }

    private object Constants {
        const val NAME_OMITTED = "."
        const val OUT_PREFIX = "$"
    }

    private class Worker(val input: BufferedReader) {
        private var currentState = State("") /* aka no value */
        private var currentLine = 0
        private var prefix = ""

        private val description = Description()
        private val userTemplates: MutableMap<String, String> = HashMap()

        private fun setCurrentState(line: String) {
            val stateName = prefix + Patterns.NAME.find(line)?.value
            currentState = description.states.getOrPut(stateName) { State(stateName) }
        }

        private fun setDirective(line: String) {
            val tokens = line.split(Patterns.SPACES)
            val value = tokens[1]
            with(Patterns.Directive) {
                when {
                    START.matches(line) -> description.start = value
                    ACCEPT.matches(line) -> description.accept = value
                    REJECT.matches(line) -> description.reject = value
                    BLANK.matches(line) -> description.blank = value
                    USER.matches(line) -> userTemplates[tokens[1]] = tokens[2]
                    else -> throw ParseException("Unexpected directive", currentLine)
                }
            }
        }

        private fun getSymbols(list: String): String = buildString {
            var isTemplate = false
            list.forEach { ch ->
                when {
                    ch == '`' -> isTemplate = !isTemplate
                    isTemplate -> append(userTemplates.getOrElse("$ch") {
                        throw ParseException("Unexpected user template `$ch`.", currentLine)
                    })
                    else -> append(ch)
                }
            }
            if (isTemplate) {
                throw ParseException("Not found close backtick (`).", currentLine)
            }
        }

        private fun setShift(line: String) {
            val tokens = line.trim().split(Patterns.SPACES)
            val (atChars, operation) = tokens

            val toStateName = tokens
                .getOrElse(2) { currentState.name }
                .run {
                    when {
                        this == currentState.name -> currentState.name
                        this == Constants.NAME_OMITTED -> currentState.name
                        startsWith(Constants.OUT_PREFIX) -> substring(1)
                        else -> prefix + this
                    }
                }
            val toState = description.states.getOrPut(toStateName) { State(toStateName) }

            getSymbols(atChars).forEach {
                val atChar = "$it"
                currentState.addShift(atChar, operation, toState, tokens.getOrElse(3) { atChar })
            }
        }

        private fun setPrefix(line: String) {
            prefix = line.split(Patterns.SPACES)[1] + "_"
        }

        private fun removePrefix() {
            prefix = ""
        }

        fun parse(): Description {
            input.lines().forEach {
                val line = it.trim()
                ++currentLine
                when {
                    line.isEmpty() || line.isBlank() -> /* Empty line */
                        Unit
                    Patterns.COMMENT.matches(line) -> /* Comment */
                        Unit
                    Patterns.STATE_NAME.matches(line) -> /* Update current state object */
                        setCurrentState(line)
                    Patterns.ENV_START.matches(line) -> /* Start of environment */
                        setPrefix(line)
                    Patterns.ENV_END.matches(line) -> /* End of environment */
                        removePrefix()
                    Patterns.SHIFT.matches(line) ->  /* Line as shift description */
                        setShift(line)
                    Patterns.Directive.GENERIC.matches(line) -> /* Line as compiler directive */
                        setDirective(line)
                    else -> /* Exception */
                        throw ParseException("Unknown line.", currentLine)
                }
            }

            return description
        }
    }

    /**
     * Takes a program's source file and return a [description][Description] of the Turing machine.
     */
    fun parse(input: Reader): Description {
        try {
            return Worker(BufferedReader(input)).parse()
        } catch (e: ParseException) {
            throw IOException("In line ${e.errorOffset}: ${e.message}")
        }
    }
}