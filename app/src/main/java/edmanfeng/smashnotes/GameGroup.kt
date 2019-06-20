package edmanfeng.smashnotes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "game_group", primaryKeys = ["game_id", "group_id"])
data class GameGroup(
    @ColumnInfo(name = "game_id") var gameId: Long = 0L,
    @ColumnInfo(name = "group_id") var groupId: Long = 0L,
    @ColumnInfo(name = "group_type") var groupType: GameRecord.GroupMode = GameRecord.GroupMode.SESSION
    ) {
}