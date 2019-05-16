package nefedov.turing.mashine

class Shift(val direction: Direction, val atChar: String, val toState: State, val putChar: String) {
    enum class Direction(private val strPresent: String) {
        RIGHT(">"),
        LEFT("<"),
        STOP("^");

        override fun toString() = strPresent
    }

    override fun toString(): String {
        return "$atChar -> $toState $putChar $direction"
    }
}