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
import java.io.IOException


/**
 * Tests for the Game Room Database
 */
@RunWith(AndroidJUnit4::class)
class GameDatabaseTest {
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
    @Throws(Exception::class)
    fun simpleWriteAndRead() {
        val game = GameRecord(
            0,
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
        gameDao.insert(game)
        val list = gameDao.getAll().value
        assertThat(list?.get(0), equalTo(game))

    }
}
