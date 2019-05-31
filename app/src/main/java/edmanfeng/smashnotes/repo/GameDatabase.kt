package edmanfeng.smashnotes.repo

import android.content.Context
import androidx.room.*
import edmanfeng.smashnotes.GameRecord

@Database(entities = arrayOf(GameRecord::class), version = 1)
@TypeConverters(Converters::class)
abstract class GameDatabase : RoomDatabase() {
    abstract fun gameDao() : GameDao

    companion object {
        @Volatile
        private var INSTANCE: GameDatabase? = null

        fun getDatabase(context: Context) : GameDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameDatabase::class.java,
                    "game_database"
                ).build()
                INSTANCE = instance // Can't return INSTANCE because it is private
                return instance
            }
        }
    }
}