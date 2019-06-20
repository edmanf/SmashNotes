package edmanfeng.smashnotes.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import edmanfeng.smashnotes.Game
import edmanfeng.smashnotes.GameRecord
import edmanfeng.smashnotes.repo.GameDatabase
import edmanfeng.smashnotes.repo.GameRepository

class OverviewViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: GameRepository
    val allGames: LiveData<List<GameRecord>>

    init {
        val gameDao = GameDatabase.getDatabase(application).gameDao()
        repository = GameRepository(gameDao)
        allGames = repository.allGames
    }

    fun getGame(game: String) : List<GameRecord> {
        return repository.getGame(game)
    }
}