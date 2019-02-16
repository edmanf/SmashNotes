package edmanfeng.smashnotes

import java.math.BigDecimal
import java.math.MathContext


class GameStats {
    companion object {
        const val PRECISION = 4

        fun getCharacterWinRate(
            gameList: List<GameRecord>,
            character: String,
            game: Game
        ) : BigDecimal {

            val countedGames = gameList.filter{
                it.game == game.toString() && it.playerCharacter == character
            }
            if (countedGames.isEmpty()) {
                return BigDecimal.ZERO
            }
            val count = BigDecimal(countedGames.filter{ it.result == "win" }.size)
            return count.divide(BigDecimal(countedGames.size), MathContext(PRECISION))
        }
    }
}