package nefedov.turing

import java.io.BufferedWriter
import java.io.FileReader
import java.io.FileWriter

private fun runParser(inFileName: String, outFileName: String) {
    val input = FileReader(inFileName)
    val output = BufferedWriter(FileWriter(outFileName))

    val description = Parser.parse(input)

    with(output) {
        with(description) {
            append("start: $start\n")
            append("accept: $accept\n")
            append("reject: $reject\n")
            append("blank: $blank\n")
            states.values.forEach { state ->
                state.shifts.values.forEach { shift -> append("$shift\n") }
            }
        }
        flush()
    }
}

private fun outFromIn(inName: String): String {
    return if (inName.find { it == '.' } != null) {
        inName.replace("(.[^.]*$)".toRegex(), ".out")
    } else {
        "$inName.out"
    }
}


fun main(args: Array<String>) {
    when (args.size) {
        1 -> runParser(args[0], outFromIn(args[0]))
        2 -> runParser(args[0], args[1])
        else -> System.err.println("Incorrect input. Ran as: \n\tretransmit <input file name> [<output file name>]")
    }
}