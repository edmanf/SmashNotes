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

    private var parentJob = Job()
    private val scope = CoroutineScope(Dispatchers.Main + parentJob)

    init {
        val gameDao = GameDatabase.getDatabase(application).gameDao()
        repository = GameRepository(gameDao)
        allGames = repository.allGames
    }

    fun insert(game: GameRecord) {
        sessionGames.add(game)
        scope.launch(Dispatchers.IO) {
            repository.insert(game)
        }
    }
}