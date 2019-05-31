package edmanfeng.smashnotes.repo

import androidx.lifecycle.LiveData
import androidx.room.*
import edmanfeng.smashnotes.GameRecord

@Dao
interface GameDao {
    @Query("SELECT * FROM game_records")
    fun getAll() : LiveData<List<GameRecord>>

    @Query("SELECT * FROM game_records WHERE id LIKE :id")
    fun getGameRecord(id: Long) : LiveData<GameRecord>

    /**
     * Returns the rowid, which because GameRecord's primary key is an Int,
     * is equal to the autogenerated primary key.
     */
    @Insert
    suspend fun insert(gameRecord: GameRecord) : Long

    @Delete
    suspend fun delete(vararg gameRecords: GameRecord)

    @Update
    suspend fun update(vararg gameRecords: GameRecord)
}