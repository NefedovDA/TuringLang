package nefedov.turing.mashine

/**
 * Represent state of Turing machine.
 *
 * @property name — name of machine.
 */
data class State(val name: String) {
    /**
     * All possible shifts of machine from this state.
     * Key is atChar. Value is a shift.
     *
     * @see Shift
     */
    val shifts: MutableMap<String, Shift> = HashMap()

    /**
     * Construct and add a new shift edge.
     * It will delete old value if [atChar] (aka key) was in [shifts] yet.
     */
    fun addShift(atChar: String, direction: String, toState: State, putChar: String) {
        shifts[atChar] = Shift(this, atChar, Shift.getDirectionByName(direction), toState, putChar)
    }
}