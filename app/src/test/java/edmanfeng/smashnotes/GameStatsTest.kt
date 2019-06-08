package edmanfeng.smashnotes

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.math.MathContext

class GameStatsTest {

    lateinit var mGameRecordList: List<GameRecord>

    @Before
    fun setup() {
        mGameRecordList = listOf(
            GameRecord(
                playerCharacter = "Mario",
                opponentCharacter = "Yoshi",
                stage = "Battlefield",
                result = GameRecord.Result.VICTORY,
                game = Game.SSBU
            ),
            GameRecord(
                playerCharacter = "Mario",
                opponentCharacter = "Mario",
                stage = "Final Destination",
                result = GameRecord.Result.LOSS,
                game = Game.SSBU
            ),
            GameRecord(
                playerCharacter = "Pikachu",
                opponentCharacter = "Mario",
                stage = "Smashville",
                result = GameRecord.Result.VICTORY,
                game = Game.SSBU),
            GameRecord(
                playerCharacter = "Mario",
                opponentCharacter = "Mario",
                stage = "Battlefield",
                result = GameRecord.Result.VICTORY,
                game =  Game.SSB4)
        )
    }
    @Test
    fun testCharacterWinRate() {
        val actual = GameStats.getWinRate(
            mGameRecordList,
            Game.SSBU,
            character = "Mario"
        )
        val expected = BigDecimal.ONE.divide(
            BigDecimal(2),
            MathContext(GameStats.PRECISION)
        )

        assertThat(actual, `is`(expected))
    }

    @Test
    fun emptyListTest() {
        val actual = GameStats.getWinRate(
            emptyList(),
            Game.SSBU,
            character = "Yoshi"
        )
        assertThat(actual, `is`(BigDecimal.ZERO))
    }

    @Test
    fun zeroWinTest() {
        val actual = GameStats.getWinRate(
            mGameRecordList,
            Game.SSBU,
            "Yoshi"
        )
        assertThat(actual, `is`(BigDecimal.ZERO))
    }

    @Test
    fun differentGameTest() {
        var actual = GameStats.getWinRate(
            mGameRecordList,
            Game.SSBU,
            character = "Mario"
        )
        assertThat(actual, `is`(BigDecimal(0.5)))


        actual = GameStats.getWinRate(
            mGameRecordList,
            Game.SSB4,
            character = "Mario"
        )
        assertThat(actual, `is`(BigDecimal.ONE))
    }
}