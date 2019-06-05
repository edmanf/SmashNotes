package edmanfeng.smashnotes

import androidx.annotation.ArrayRes
import androidx.annotation.IntegerRes

enum class Game {
    SSB64, SSBM, SSBB, PM, SSB4, SSBU;

    /**
     * Returns the stage array resource id associated with the game
     */
    fun getStageArrayResourceId() : Int {
        // TODO: add PM, SSF2
        return when (this) {
            SSBU -> R.array.stagesUltimate
            SSB4 -> R.array.stages4wiiu
            SSBB -> R.array.stagesBrawl
            SSBM -> R.array.stagesMelee
            SSB64 -> R.array.stages64
            else -> R.array.stagesUltimate
        }
    }

    /**
     * Returns the character array resource id associated with the game
     */
    fun getCharacterArrayResourceId() : Int {
        // TODO: add PM, SSF2
        return when (this) {
            SSBU -> R.array.charactersUltimate
            SSB4 -> R.array.characters4
            SSBB -> R.array.charactersBrawl
            SSBM -> R.array.charactersMelee
            SSB64 -> R.array.characters64
            else -> R.array.charactersUltimate
        }
    }
}

/**
 * Returns a List containing a String representation of all values of
 * Game as well as a default value. If default is null, no value is added
 */
fun getAdapterList(default: String? = "All") : List<String>{
    val array = mutableListOf<String>()
    if (default != null) {
        array.add(default)
    }
    Game.values().forEach { array.add(it.toString()) }
    return array
}

