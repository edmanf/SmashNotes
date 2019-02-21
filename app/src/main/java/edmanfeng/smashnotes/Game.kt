package edmanfeng.smashnotes

enum class Game {
    SSB64, SSBM, SSBB, PM, SSB4, SSBU;

    companion object {
        /**
         * Returns a List containing a String representation of all values of
         * Game as well as a default value. If default is null, no value is added
         */
        fun getAdapterList(default: String? = "All") : List<String>{
            val array = mutableListOf<String>()
            if (default != null) {
                array.add(default)
            }
            values().forEach { array.add(it.toString()) }
            return array
        }
    }
}