package edmanfeng.smashnotes.repo

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import edmanfeng.smashnotes.Game

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

    fun getGame(game: Game?) : List<GameRecord> {
        if (game == null) {
            return allGames.value ?: listOf()
        }
        return allGames.value?.filter {it.game == game} ?: listOf()
    }

    fun getGame(game: String) : List<GameRecord> {
        if (game == "All") {
            return getGame(null)
        }
        return getGame(Game.valueOf(game))
    }

    fun getGroup(groupId: Long) : List<GameRecord> {
        return listOf()
    }
}