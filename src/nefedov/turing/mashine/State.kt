package nefedov.turing.mashine

data class State(val name: String) {
    val shifts: List<Shift> = listOf()

    override fun toString(): String {
        return buildString {
            shifts.forEach { shift -> append("$name $shift\n") }
        }
    }
}