package nefedov.turing.mashine

class Description {
    var start: String = "S"
    var accept: String = "AC"
    var reject: String = "RJ"
    var blank: String = "_"

    val states: MutableMap<String, State> = HashMap()

    fun setIfDefault(key: String, value: String) {
        when (key) {
            "start" -> start = value
            "accept" -> accept = value
            "reject" -> reject = value
            "blank" -> blank = value
        }
    }
}