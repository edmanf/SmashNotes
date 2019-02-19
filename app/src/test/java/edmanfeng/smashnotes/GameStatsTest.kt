package edmanfeng.smashnotes

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.math.MathContext

class GameStatsTest {

    lateinit var mGameRecordList: List<GameRecord>

    @Before
    fun setup() {
        mGameRecordList = listOf(
            GameRecord(0,
                "Mario", "Yoshi",
                "ESAM",
                "Battlefield", true,
                "win", 192999,
                "I LOSE", Game.SSBU.toString()
            ),
            GameRecord(0,
                "Mario", "Mario",
                "Zero",
                "Final Destination", false,
                "lose", 291994,
                "WHAT", Game.SSBU.toString()
            ),
            GameRecord(0,
                "Pikachu", "Mario",
                "A",
                "Smashville", true,
                "win", 299129,
                "HUH", Game.SSBU.toString()),
            GameRecord(0,
                "Mario", "Mario",
                "Nairo",
                "Battlefield", false,
                "win", 0,
                "HUH", Game.SSB4.toString())
        )
    }
    @Test
    fun testCharacterWinRate() {
        assertEquals(
            BigDecimal.ONE.divide(BigDecimal(2), MathContext(GameStats.PRECISION)),
            GameStats.getWinRate(mGameRecordList, Game.SSBU, character = "Mario")
        )
    }

    @Test
    fun emptyListTest() {
        assertEquals(
            BigDecimal.ZERO,
            GameStats.getWinRate(emptyList(),Game.SSBU, character = "Yoshi")
        )
    }

    @Test
    fun zeroWinTest() {
        assertEquals(
            BigDecimal.ZERO,
            GameStats.getWinRate(mGameRecordList, Game.SSBU, "Yoshi")
        )
    }

    @Test
    fun differentGameTest() {
        assertEquals(
            BigDecimal(0.5), GameStats.getWinRate(mGameRecordList, Game.SSBU, character = "Mario")
        )
        assertEquals(
            BigDecimal.ONE, GameStats.getWinRate(mGameRecordList, Game.SSB4, character = "Mario")
        )
    }
}