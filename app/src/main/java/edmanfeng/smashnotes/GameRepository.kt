package edmanfeng.smashnotes

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class GameRepository(private val gameDao: GameDao) {
    val allGames: LiveData<List<GameRecord>> = gameDao.getAll()

    @WorkerThread
    suspend fun insert(game: GameRecord) {
        gameDao.insert(game)
    }
}