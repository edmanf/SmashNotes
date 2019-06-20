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
        if (game == "All") {
            return getGame(null)
        }
        return getGame(Game.valueOf(game))
    }

    fun getGame(game: Game?) : List<GameRecord> {
        if (game == null) {
            return allGames.value ?: listOf()
        }
        return allGames.value?.filter {it.game == game} ?: listOf()
    }
}