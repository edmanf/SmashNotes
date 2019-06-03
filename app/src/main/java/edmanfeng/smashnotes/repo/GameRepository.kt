package edmanfeng.smashnotes.repo

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import edmanfeng.smashnotes.GameRecord

class GameRepository(private val gameDao: GameDao) {
    val allGames: LiveData<List<GameRecord>> = gameDao.getAll()

    @WorkerThread
    suspend fun insert(game: GameRecord) : Long{
        return gameDao.insert(game)
    }

    @WorkerThread
    suspend fun delete(game: GameRecord) {
        gameDao.delete(game)
    }

    @WorkerThread
    suspend fun update(game: GameRecord) {
        gameDao.update(game)
    }


    fun getGameRecord(id: Long) : LiveData<GameRecord> {
        return gameDao.getGameRecord(id)
    }
}