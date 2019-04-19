package edmanfeng.smashnotes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import android.content.Context
import androidx.room.Room
import edmanfeng.smashnotes.repo.GameDao
import edmanfeng.smashnotes.repo.GameDatabase
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import testUtil.LiveDataTestUtil.getValue
import java.io.IOException


/**
 * Tests for the Game Room Database
 */
class GameDatabaseInstrumentedTest {
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
    fun listDelete() {
        val game1 = GameRecord(id = 6, notes = "NOTES")
        val game2 = GameRecord(id = 27, playerCharacter = "Yoshi")
        val game3 = GameRecord(id = 10, hazards = true)
        gameDao.insert(game1)
        gameDao.insert(game2)
        gameDao.insert(game3)
        assertThat(getValue(gameDao.getAll()).size, equalTo(3))
        gameDao.delete(listOf(game3, game1, game2))
        assert(getValue(gameDao.getAll()).isEmpty())

        val game4 = GameRecord(id = 3, stage = "Final Destination")
        gameDao.insert(game1)
        gameDao.insert(game2)
        gameDao.insert(game3)
        gameDao.insert(game4)
        assertThat(getValue(gameDao.getAll()).size, equalTo(4))
        gameDao.delete(listOf(game1, game3))
        var list = getValue(gameDao.getAll())
        assertThat(list.size, equalTo(2))
        assert(list.contains(game2))
        assert(list.contains(game4))

        gameDao.delete(listOf())
        list = getValue(gameDao.getAll())
        assertThat(list.size, equalTo(2))
        assert(list.contains(game2))
        assert(list.contains(game4))

        gameDao.delete(listOf(game2))
        list = getValue(gameDao.getAll())
        assertThat(list.size, equalTo(1))
        assert(list.contains(game4))

        gameDao.delete(listOf(game1, game4))
        list = getValue(gameDao.getAll())
        assert(list.isEmpty())
    }

    @Test
    @Throws(IOException::class)
    fun singleDelete() {
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

    @Test
    @Throws(IOException::class)
    fun insert() {
        var game = GameRecord(
            20,
            "b",
            "c",
            "d",
            "e",
            true,
            "g",
            1,
            "j",
            "k"
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
            "a",
            20,
            "fdksal",
            "no"
        )
        gameDao.insert(game2)
        list = getValue(gameDao.getAll())
        assertThat(list.size, equalTo(2))
        assertThat(list.get(0), equalTo(game))
        assertThat(list.get(1), equalTo(game2))

    }
}
