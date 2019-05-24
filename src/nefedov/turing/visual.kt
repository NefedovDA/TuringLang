package nefedov.turing

import java.io.FileReader

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        System.err.println("Incorrect input. Ran as: \n\tvisual <input file name>")
    }
    val description = Parser.parse(FileReader(args[0]))
    Interpreter.interpret(description, "110110")
}