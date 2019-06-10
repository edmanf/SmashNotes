package edmanfeng.smashnotes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import android.content.Context
import androidx.room.Room
import edmanfeng.smashnotes.repo.GameDao
import edmanfeng.smashnotes.repo.GameDatabase
import kotlinx.coroutines.*
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import testUtil.LiveDataTestUtil.getValue
import java.io.IOException


/**
 * Tests for the Game Room Database.
 *
 * Although it runs on a device, it does not need an activity
 * and is faster than UI tests
 */
class GameDatabaseUnitTest {
    private lateinit var gameDao: GameDao
    private lateinit var db: GameDatabase

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, GameDatabase::class.java).build()
        gameDao = db.gameDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(IOException::class)
    fun update() {

        runBlocking {
            var game1 = GameRecord(playerCharacter = "Lucas", result = GameRecord.Result.VICTORY)
            var game2 = GameRecord(playerCharacter = "Yoshi", result = GameRecord.Result.VICTORY)
            var game3 = GameRecord(stage = "Final Destination")


            val id1 = gameDao.insert(game1)
            val id2 = gameDao.insert(game2)
            val id3 = gameDao.insert(game3)


            game1 = getValue(gameDao.getGameRecord(id1))
            game1.playerCharacter = "Ness"
            gameDao.update(game1)

            var games = getValue(gameDao.getAll())
            assertThat(games[0].playerCharacter, equalTo("Ness"))
            assertThat(games[1].playerCharacter, equalTo("Yoshi"))
            assertThat(games[2].playerCharacter, equalTo("Mario"))

            game2 = getValue(gameDao.getGameRecord(id2))
            game3 = getValue(gameDao.getGameRecord(id3))
            game2.result = GameRecord.Result.LOSS
            game3.stage = "Lylat Cruise"
            gameDao.update(game2, game3)
            games = getValue(gameDao.getAll())

            assertThat(games[0].playerCharacter, equalTo("Ness"))
            assertThat(games[0].result, equalTo(GameRecord.Result.VICTORY))
            assertThat(games[1].playerCharacter, equalTo("Yoshi"))
            assertThat(games[1].result, equalTo(GameRecord.Result.LOSS))
            assertThat(games[2].playerCharacter, equalTo("Mario"))
            assertThat(games[2].stage, equalTo("Lylat Cruise"))
        }


    }

    @Test
    @Throws(IOException::class)
    fun listDelete() {
        val game1 = GameRecord(id = 6, notes = "NOTES")
        val game2 = GameRecord(id = 27, playerCharacter = "Yoshi")
        val game3 = GameRecord(id = 10, hazards = true)
        runBlocking {
            gameDao.insert(game1)
            gameDao.insert(game2)
            gameDao.insert(game3)
            assertThat(getValue(gameDao.getAll()).size, equalTo(3))
            gameDao.delete(game3, game1, game2)
            assert(getValue(gameDao.getAll()).isEmpty())

            val game4 = GameRecord(id = 3, stage = "Final Destination")
            gameDao.insert(game1)
            gameDao.insert(game2)
            gameDao.insert(game3)
            gameDao.insert(game4)
            assertThat(getValue(gameDao.getAll()).size, equalTo(4))
            gameDao.delete(game1, game3)
            var list = getValue(gameDao.getAll())
            assertThat(list.size, equalTo(2))
            assert(list.contains(game2))
            assert(list.contains(game4))

            gameDao.delete()
            list = getValue(gameDao.getAll())
            assertThat(list.size, equalTo(2))
            assert(list.contains(game2))
            assert(list.contains(game4))

            gameDao.delete(game2)
            list = getValue(gameDao.getAll())
            assertThat(list.size, equalTo(1))
            assert(list.contains(game4))

            gameDao.delete(game1, game4)
            list = getValue(gameDao.getAll())
            assert(list.isEmpty())
        }
    }

    @Test
    @Throws(IOException::class)
    fun singleDelete() {
        runBlocking {
            val game1 = GameRecord(id = 5)
            gameDao.insert(game1)
            val game2 = GameRecord(id = 10)
            gameDao.insert(game2)
            var list = getValue(gameDao.getAll())
            assertThat(list.size, equalTo(2))
            assert(list.contains(game2))
            assert(list.contains(game1))
            gameDao.delete(game2)
            list = getValue(gameDao.getAll())
            assertThat(list.size, equalTo(1))
            assert(list.contains(game1))

            val game3 = GameRecord(id = 4, notes = "NOOO")
            gameDao.insert(game3)
            gameDao.delete(game1)
            list = getValue(gameDao.getAll())
            assertThat(list.size, equalTo(1))
            assert(list.contains(game3))

            gameDao.delete(game3)
            assert(getValue(gameDao.getAll()).isEmpty())
        }
    }

    @Test
    @Throws(IOException::class)
    fun insert() {
        runBlocking {
            var game = GameRecord(
                20,
                "b",
                "c",
                "d",
                "e",
                true,
                GameRecord.Result.VICTORY,
                1,
                "j",
                Game.SSBM
            )
            assertThat(getValue(gameDao.getAll()).size, equalTo(0))
            gameDao.insert(game)
            var list = getValue(gameDao.getAll())
            assertThat(list.get(0), equalTo(game))
            assertThat(list.size, equalTo(1))

            val game2 = GameRecord(
                25,
                "c",
                "d",
                "e",
                "f",
                false,
                GameRecord.Result.VICTORY,
                20,
                "fdksal",
                Game.SSB64
            )
            gameDao.insert(game2)
            list = getValue(gameDao.getAll())
            assertThat(list.size, equalTo(2))
            assertThat(list.get(0), equalTo(game))
            assertThat(list.get(1), equalTo(game2))

        }
    }
}
