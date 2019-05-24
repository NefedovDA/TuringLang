package nefedov.turing.mashine

/**
 * Represents a shift of turing machine.
 *
 * @property atState — current state of machine.
 * @property atChar — char that will be replaced
 * @property direction — where machine will be: on left, on right or on place
 * @property toState — state in which machine will
 * @property putChar — char for replacement
 */
data class Shift(
    val atState: State,
    val atChar: String,
    val direction: Direction,
    val toState: State,
    val putChar: String
) {
    enum class Direction(private val strPresent: String) {
        RIGHT(">"),
        LEFT("<"),
        STOP("^");

        override fun toString() = strPresent
    }

    /**
     * Construct line present shift.
     * Format is:
     * <current char> -> <name of next state> <replacing char> <operation: <, > or ^>
     */
    override fun toString(): String {
        return "${atState.name} $atChar -> ${toState.name} $putChar $direction"
    }

    companion object {
        /**
         * Return [Direction].TOKEN by its name.
         */
        fun getDirectionByName(name: String) = when (name) {
            ">" -> Direction.RIGHT
            "<" -> Direction.LEFT
            "^" -> Direction.STOP
            else -> throw  IllegalArgumentException("Was expected a name: <, > or ^ but was `$name`")
        }
    }
}