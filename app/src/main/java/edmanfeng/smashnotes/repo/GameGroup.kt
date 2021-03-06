package edmanfeng.smashnotes.repo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey


@Entity(
    tableName = "group",
    primaryKeys = ["game_id", "group_id"],
    foreignKeys = [ForeignKey(
        entity = GameRecord::class,
        parentColumns = ["id"],
        childColumns = ["game_id"]
    )]
)
data class GameGroup(
    @ColumnInfo(name = "game_id")  var gameId: Long = 0L,
    @ColumnInfo(name = "group_id") var groupId: Long = 0L,
    @ColumnInfo(name = "group_type") var groupType: GameRecord.GroupMode = GameRecord.GroupMode.SESSION
    )