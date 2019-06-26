package edmanfeng.smashnotes.repo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import edmanfeng.smashnotes.Game

/**
 * GroupMode = BO3, BO5, session
 * GameType = practice, quickplay, anther's, tournament
 * Tag
 */
@Entity(tableName = "game_records")
data class GameRecord(
    @PrimaryKey(autoGenerate = true) var id : Long = 0,
    @ColumnInfo(name = "player_character") var playerCharacter: String = "Mario",
    @ColumnInfo(name = "opponent_character") var opponentCharacter: String = "Mario",
    @ColumnInfo(name = "opponent_tag") var opponentTag : String = "John",
    @ColumnInfo(name = "stage") var stage : String = "Battlefield",
    @ColumnInfo(name = "hazards") var hazards : Boolean = false,
    @ColumnInfo(name = "result") var result : Result = Result.VICTORY,
    @ColumnInfo(name = "gsp") var gsp : Int = 0,
    @ColumnInfo(name = "notes") var notes : String = "",
    @ColumnInfo(name = "game") var game : Game = Game.SSBU,
    @ColumnInfo(name = "group_mode") var groupMode : GroupMode = GroupMode.SESSION,
    @ColumnInfo(name = "game_number") var gameNumber : Int = 0,
    @ColumnInfo(name = "game_type") var gameType : GameType = GameType.PRACTICE
) {
    companion object {
        const val NEW_GAME_ID: Long = -1L

        fun newGame() : GameRecord {
            return GameRecord(NEW_GAME_ID)
        }
    }

    fun isNewGame() : Boolean {
        return id == NEW_GAME_ID
    }

    fun isVictory() : Boolean {
        return result == Result.VICTORY
    }

    enum class Result {
        VICTORY, LOSS;

        override fun toString() : String {
            return when(this) {
                VICTORY -> "Victory"
                LOSS -> "Loss"
            }
        }
    }

    enum class GroupMode {
        BO3, BO5, SESSION
    }

    enum class GameType {
        PRACTICE, ANTHERS, QUICKPLAY, TOURNAMENT, FORGLORY, FREEPLAY
    }
}