package edmanfeng.smashnotes

import java.math.BigDecimal
import java.math.MathContext


object GameStats {
    const val PRECISION = 4

    /**
     * Returns the proportion of games won from gameList as a BigDecimal.
     * Only GameRecords that match the game and optional parameters character,
     * and stage. Also only takes into account the lastGames most recent games
     * from the list.
     */
    fun getWinRate(
        gameList: List<GameRecord>,
        game: Game,
        character: String? = null,
        stage: String? = null,
        lastGames: Int = Integer.MAX_VALUE
    ) : BigDecimal {

        val countedGames = filterGames(
            gameList,
            game,
            character = character,
            stage = stage,
            lastGames = lastGames
        )
        if (countedGames.isEmpty()) {
            return BigDecimal.ZERO
        }
        val count = BigDecimal(countedGames.count {
            it.result == GameRecord.Result.VICTORY
        })
        return count.divide(
            BigDecimal(countedGames.size),
            MathContext(PRECISION)
        )
    }

    private fun filterGames(
        gameList: List<GameRecord>,
        game: Game,
        character: String?,
        stage: String?,
        lastGames: Int
    ) : List<GameRecord> {
        val filteredGames = gameList.filter {
            it.game == game
                    && (character == null || it.playerCharacter == character)
                    && (stage == null || it.stage == stage)
        }
        if (filteredGames.size <= lastGames) {
            return filteredGames
        }

        val startIndex = filteredGames.size-lastGames
        return filteredGames.slice(startIndex.until(filteredGames.size))
    }
}
