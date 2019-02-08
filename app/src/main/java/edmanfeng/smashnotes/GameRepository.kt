package edmanfeng.smashnotes

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class GameRepository(private val gameDao: GameDao) {
    val allGames: LiveData<List<GameRecord>> = gameDao.getAll()

    @WorkerThread
    suspend fun insert(game: GameRecord) {
        gameDao.insert(game)
    }

    @WorkerThread
    suspend fun delete(game: GameRecord) {
        gameDao.delete(game)
    }

    @WorkerThread
    suspend fun deleteAll(games: List<GameRecord>) {
        gameDao.deleteAll(games)
    }

    @WorkerThread
    suspend fun getGame(game: Game) = gameDao.getGame(game)
}