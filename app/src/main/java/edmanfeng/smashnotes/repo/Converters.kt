package edmanfeng.smashnotes.repo

import androidx.room.TypeConverter
import edmanfeng.smashnotes.Game
import edmanfeng.smashnotes.GameRecord


class Converters {
    @TypeConverter
    fun resultEnumToString(result: GameRecord.Result) : String {
        return if(result == GameRecord.Result.VICTORY) {
            ROOM_RESULT_VICTORY
        }  else {
            ROOM_RESULT_LOSS
        }
    }

    @TypeConverter
    fun resultStringToEnum(result: String) : GameRecord.Result {
        return if(result == ROOM_RESULT_VICTORY) {
            GameRecord.Result.VICTORY
        } else {
            GameRecord.Result.LOSS
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

    @TypeConverter
    fun groupModeEnumToString(groupMode: GameRecord.GroupMode) : String {
        return groupMode.toString()
    }

    @TypeConverter
    fun groupModeStringToEnum(groupMode: String) : GameRecord.GroupMode {
        return GameRecord.GroupMode.valueOf(groupMode)
    }

    @TypeConverter
    fun gameTypeEnumToString(gameType: GameRecord.GameType) : String {
        return gameType.toString()
    }

    @TypeConverter
    fun gameTypeStringToEnum(gameType: String) : GameRecord.GameType {
        return GameRecord.GameType.valueOf(gameType)
    }
}


private const val ROOM_RESULT_VICTORY = "VICTORY"
private const val ROOM_RESULT_LOSS = "LOSS"