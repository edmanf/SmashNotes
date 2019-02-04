package edmanfeng.smashnotes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_records")
data class GameRecord(
    @PrimaryKey(autoGenerate = true) var id : Int = 0,
    @ColumnInfo(name = "player_character") var playerCharacter: String,
    @ColumnInfo(name = "opponent_character") var opponentCharacter: String,
    @ColumnInfo(name = "opponent_tag") var opponentTag : String,
    @ColumnInfo(name = "stage") var stage : String,
    @ColumnInfo(name = "hazards") var hazards : Boolean,
    @ColumnInfo(name = "result") var result : String,
    @ColumnInfo(name = "gsp") var gsp : Int,
    @ColumnInfo(name = "notes") var notes : String,
    @ColumnInfo(name = "game") var game : String
)