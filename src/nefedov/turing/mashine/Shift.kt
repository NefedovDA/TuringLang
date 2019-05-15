package nefedov.turing.mashine

class Shift(val direction: Direction, val toState: State, val putChar: String) {
    enum class Direction { RIGHT, LEFT, STOP }
}