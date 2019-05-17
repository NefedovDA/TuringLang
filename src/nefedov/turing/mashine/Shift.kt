package nefedov.turing.mashine

import java.lang.IllegalArgumentException

data class Shift(val direction: Direction, val atChar: String, val toState: State, val putChar: String) {
    enum class Direction(private val strPresent: String) {
        RIGHT(">"),
        LEFT("<"),
        STOP("^");

        override fun toString() = strPresent
    }

    override fun toString(): String {
        return "$atChar -> ${toState.name} $putChar $direction"
    }

    companion object {
        fun getDirectionByName(name: String) = when (name) {
            ">" -> Direction.RIGHT
            "<" -> Direction.LEFT
            "^" -> Direction.STOP
            else -> throw  IllegalArgumentException("Was expected a name: <, > or ^")
        }
    }
}