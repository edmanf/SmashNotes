package edmanfeng.smashnotes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import edmanfeng.smashnotes.repo.GameDatabase
import edmanfeng.smashnotes.repo.GameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: GameRepository

    val sessionGames = mutableListOf<GameRecord>() // local copy of session games
    val allGames: LiveData<List<GameRecord>>

    private var parentJob = Job()
    private val scope = CoroutineScope(Dispatchers.Main + parentJob)

    init {
        val gameDao = GameDatabase.getDatabase(application).gameDao()
        repository = GameRepository(gameDao)
        allGames = repository.allGames
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

    fun getGameRecord(id: Long) : LiveData<GameRecord> {
        return repository.getGameRecord(id)
    }

    fun insert(game: GameRecord) {
        scope.launch(Dispatchers.IO) {
            val id = repository.insert(game)
            game.id = id
        }
        // Currently not guaranteed that the id will change before a user tries to click it
        sessionGames.add(game)
    }

    fun delete(game: GameRecord) {
        sessionGames.remove(game)
        scope.launch(Dispatchers.IO) {
            repository.delete(game)
        }
    }

    fun deleteAll() {
        // TODO: Check if assigning to mutableListOf() would work
        sessionGames.removeAll(sessionGames)
    }

    fun updateGame(game: GameRecord) {
        scope.launch(Dispatchers.IO) {
            repository.update(game)
        }
    }
}