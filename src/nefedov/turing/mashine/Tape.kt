package nefedov.turing.mashine

class Tape(input: String, blank: String, offset: Int = 10) {
    private val tape =
        (blank.repeat(offset) + input + blank.repeat(offset))
            .toCharArray().map { "$it" } as MutableList
    private var currentInd = offset

    fun run(): String = tape[currentInd]

    fun back(putChar: String): String {
        tape[currentInd] = putChar
        currentInd--
        return tape[currentInd]
    }

    fun stay(putChar: String): String {
        tape[currentInd] = putChar
        return tape[currentInd]
    }

    fun forward(putChar: String): String {
        tape[currentInd] = putChar
        currentInd++
        return tape[currentInd]
    }

    fun print() {
        println(buildString {
            tape.forEach { append("$it ") }
        })
        println(" ".repeat(currentInd * 2) + "^")
    }
}