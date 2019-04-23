package edmanfeng.smashnotes.repo

import androidx.lifecycle.LiveData
import androidx.room.*
import edmanfeng.smashnotes.GameRecord

@Dao
interface GameDao {
    @Query("SELECT * FROM game_records")
    fun getAll() : LiveData<List<GameRecord>>

    @Query("SELECT * FROM game_records WHERE id LIKE :id")
    fun getGameRecord(id: Long)

    @Insert
    fun insert(gameRecord: GameRecord) : Long

    @Delete
    fun delete(vararg gameRecords: GameRecord)

    @Update
    fun update(vararg gameRecords: GameRecord)
}