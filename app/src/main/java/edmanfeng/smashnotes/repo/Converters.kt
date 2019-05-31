package edmanfeng.smashnotes.repo

import androidx.room.TypeConverter
import edmanfeng.smashnotes.Game
import edmanfeng.smashnotes.GameRecord


class Converters {
    @TypeConverter
    fun resultEnumToString(result: GameRecord.RESULT) : String {
        return if(result == GameRecord.RESULT.VICTORY) {
            ROOM_RESULT_VICTORY
        }  else {
            ROOM_RESULT_LOSS
        }
    }

    @TypeConverter
    fun resultStringToEnum(result: String) : GameRecord.RESULT {
        return if(result == ROOM_RESULT_VICTORY) {
            GameRecord.RESULT.VICTORY
        } else {
            GameRecord.RESULT.LOSS
        }
    }

    @TypeConverter
    fun gameEnumToString(game: Game) : String {
        return game.toString()
    }

    @TypeConverter
    fun gameStringToGame(game: String) : Game {
        return when (game) {
            "SSBU" -> Game.SSBU
            "SSB4" -> Game.SSB4
            "SSBB" -> Game.SSBB
            "SSBM" -> Game.SSBM
            "SSB64" -> Game.SSB64
            "PM" -> Game.PM
            else -> Game.SSBU
        }
    }
}


private const val ROOM_RESULT_VICTORY = "VICTORY"
private const val ROOM_RESULT_LOSS = "LOSS"