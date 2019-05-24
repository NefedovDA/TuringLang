package nefedov.turing

import java.io.FileReader

fun main(args: Array<String>) {
    if (args.size != 3) {
        System.err.println(
            "Incorrect input. Ran as: \n" +
                    "\tvisual <input file name> <input line for turing machine> <size of offset>"
        )
    }
    val description = Parser.parse(FileReader(args[0]))
    Interpreter.interpret(description, args[1], args[2].toInt())
}