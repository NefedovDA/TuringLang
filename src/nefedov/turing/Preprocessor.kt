package nefedov.turing

class Preprocessor(
    val start: String,
    val accept: String,
    val reject: String,
    val blank: String,
    val alphabet: String,
    val special: String
) {
    constructor(values: Map<String, String>) : this(
        start = values.getValue("start"),
        accept = values.getValue("accept"),
        reject = values.getValue("reject"),
        blank = values.getValue("blank"),
        alphabet = values.getValue("alphabet"),
        special = values.getValue("special")
    )

    override fun toString(): String {
        return """
            start: $start
            accept: $accept
            reject: $reject
            blank: $blank

        """.trimIndent()
    }

    companion object {
        fun getDefaultValues(): MutableMap<String, String> = mutableMapOf(
            "start" to "S",
            "accept" to "AC",
            "reject" to "RJ",
            "blank" to "_",
            "alphabet" to "01",
            "special" to ""
        )
    }
}