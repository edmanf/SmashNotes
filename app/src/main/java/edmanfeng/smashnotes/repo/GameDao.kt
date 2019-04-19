package edmanfeng.smashnotes.repo

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import edmanfeng.smashnotes.GameRecord

@Dao
interface GameDao {
    @Query("SELECT * FROM game_records")
    fun getAll() : LiveData<List<GameRecord>>

    @Insert
    fun insert(gameRecord: GameRecord)

    @Delete
    fun delete(gameRecord: GameRecord)

    @Delete
    fun delete(gameRecords: List<GameRecord>)
}