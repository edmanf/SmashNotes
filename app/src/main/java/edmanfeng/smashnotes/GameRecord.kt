package edmanfeng.smashnotes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_records")
data class GameRecord(
    @PrimaryKey(autoGenerate = true) var id : Int = 1,
    @ColumnInfo(name = "player_character") var playerCharacter: String = "Mario",
    @ColumnInfo(name = "opponent_character") var opponentCharacter: String = "Mario",
    @ColumnInfo(name = "opponent_tag") var opponentTag : String = "John",
    @ColumnInfo(name = "stage") var stage : String = "Battlefield",
    @ColumnInfo(name = "hazards") var hazards : Boolean = false,
    @ColumnInfo(name = "result") var result : String = "win",
    @ColumnInfo(name = "gsp") var gsp : Int = 3500000,
    @ColumnInfo(name = "notes") var notes : String = "",
    @ColumnInfo(name = "game") var game : String = Game.SSBU.toString()
)