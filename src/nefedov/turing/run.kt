package nefedov.turing

import java.io.*

fun main() {
    val task = mapOf("A" to "zero", "B" to "aplusb")
    val fileName = task["A"]
    val input = FileReader("$fileName.in")
    val output = FileWriter("$fileName.out")
}