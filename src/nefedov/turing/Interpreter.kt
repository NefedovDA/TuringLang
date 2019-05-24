package nefedov.turing

import nefedov.turing.mashine.Description
import nefedov.turing.mashine.Shift
import nefedov.turing.mashine.State
import nefedov.turing.mashine.Tape

object Interpreter {
    private fun print(tape: Tape, state: State) {
        println(state.name + ":")
        tape.print()
        println()
    }

    fun interpret(description: Description, input: String, offset: Int) = with(description) {
        val tape = Tape(input, description.blank, offset)
        var atChar = tape.run()
        var currentState = states[start] ?: throw Exception("NO START STATE")
        print(tape, currentState)
        while (true) {
            when (currentState.name) {
                accept -> return println("ACCEPT")
                reject -> return println("REJECT")
            }

            val shift = currentState.shifts.getOrElse(atChar) { return println("REJECT") }
            currentState = shift.toState
            atChar = when (shift.direction) {
                Shift.Direction.RIGHT -> tape.forward(shift.putChar)
                Shift.Direction.LEFT -> tape.back(shift.putChar)
                Shift.Direction.STOP -> tape.stay(shift.putChar)
            }
            print(tape, currentState)
        }
    }
}