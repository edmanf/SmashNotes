package edmanfeng.smashnotes

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameDao {
    @Query("SELECT * FROM game_records")
    fun getAll() : LiveData<List<GameRecord>>

    @Insert
    fun insert(gameRecord: GameRecord)

    @Delete
    fun delete(gameRecord: GameRecord)

    @Delete
    fun deleteAll(gameRecord: List<GameRecord>)
}