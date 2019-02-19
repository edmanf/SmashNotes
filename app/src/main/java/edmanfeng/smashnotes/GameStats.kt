package edmanfeng.smashnotes

import java.math.BigDecimal
import java.math.MathContext


class GameStats {
    companion object {
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
            val count = BigDecimal(countedGames.filter{
                it.result == "win"
            }.size)
            return count.divide(
                BigDecimal(countedGames.size),
                MathContext(PRECISION)
            )
        }

        private fun filterGames(
            gameList: List<GameRecord>,
            game: Game,
            character: String? = null,
            stage: String? = null,
            lastGames: Int = Integer.MAX_VALUE
        ) : List<GameRecord> {
            val filteredGames = gameList.filter {
                it.game == game.toString()
                        && (character == null || it.playerCharacter == character)
                        && (stage == null || it.stage == stage)
            }
            if (filteredGames.size <= lastGames) {
                return filteredGames
            }
            return filteredGames.slice(0..lastGames)
        }
    }
}