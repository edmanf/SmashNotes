package edmanfeng.smashnotes

enum class Game {
    SSB64, SSBM, SSBB, PM, SSB4, SSBU;

    companion object {
        /**
         * Returns a List containing a String representation of all values of
         * Game as well as a default value
         */
        fun getAdapterList(default: String = "All") : List<String>{
            val array = mutableListOf(default)
            values().forEach { array.add(it.toString()) }
            return array
        }
    }
}