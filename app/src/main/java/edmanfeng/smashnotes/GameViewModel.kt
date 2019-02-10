package edmanfeng.smashnotes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: GameRepository

    val sessionGames = mutableListOf<GameRecord>() // local copy of session games
    val allGames: LiveData<List<GameRecord>>
    val games: LiveData<List<GameRecord>>

    private var parentJob = Job()
    private val scope = CoroutineScope(Dispatchers.Main + parentJob)

    init {
        val gameDao = GameDatabase.getDatabase(application).gameDao()
        repository = GameRepository(gameDao)
        allGames = repository.allGames
        games = repository.allGames
    }

    fun getGame(game: Game?) : List<GameRecord> {
        if (game == null) {
            return allGames.value ?: listOf()
        }
        return allGames.value?.filter {it.game == game.toString()} ?: listOf()
    }

    fun getGame(game: String) : List<GameRecord> {
        if (game == "All") {
            return getGame(null)
        }
        return getGame(Game.valueOf(game))
    }

    fun insert(game: GameRecord) {
        sessionGames.add(game)
        scope.launch(Dispatchers.IO) {
            repository.insert(game)
        }
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
}