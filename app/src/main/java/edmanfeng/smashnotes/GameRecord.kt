package edmanfeng.smashnotes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_records")
data class GameRecord(
    @PrimaryKey(autoGenerate = true) var id : Long = 0,
    @ColumnInfo(name = "player_character") var playerCharacter: String = "Mario",
    @ColumnInfo(name = "opponent_character") var opponentCharacter: String = "Mario",
    @ColumnInfo(name = "opponent_tag") var opponentTag : String = "John",
    @ColumnInfo(name = "stage") var stage : String = "Battlefield",
    @ColumnInfo(name = "hazards") var hazards : Boolean = false,
    @ColumnInfo(name = "result") var result : RESULT = RESULT.VICTORY,
    @ColumnInfo(name = "gsp") var gsp : Int = 3500000,
    @ColumnInfo(name = "notes") var notes : String = "",
    @ColumnInfo(name = "game") var game : Game = Game.SSBU
) {
    companion object {
        const val NEW_GAME_ID: Long = -1L
    }

    enum class RESULT {
        VICTORY, LOSS;

        override fun toString() : String {
            return when(this) {
                VICTORY -> "Victory"
                LOSS -> "Loss"
            }
        }
    }
}