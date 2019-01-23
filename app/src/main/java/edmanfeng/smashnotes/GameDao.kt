package edmanfeng.smashnotes

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameDao {
    @Query("SELECT * FROM game_records")
    fun getAll() : List<GameRecord>

    @Insert
    fun insert(gameRecord: GameRecord)
}