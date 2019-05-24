package nefedov.turing

import java.io.FileReader

fun main(args: Array<String>) {

    val (input, offset) = when (args.size) {
        1 -> {
            println("Input line: ")
            val input = readLine()!!
            input to 8
        }
        2 -> args[1] to 8
        3 -> args[1] to args[2].toInt()
        else -> return System.err.println(
            "Incorrect input. Ran as: \n" +
                    "\tvisual <input file name> [<input line for turing machine> [<size of offset>]]"
        )

    }
    val description = Parser.parse(FileReader(args[0]))
    Interpreter.interpret(description, input, offset)
}