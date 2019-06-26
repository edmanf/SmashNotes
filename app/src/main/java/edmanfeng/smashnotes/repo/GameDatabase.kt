package edmanfeng.smashnotes.repo

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [GameRecord::class, GameGroup::class], version = 3)
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
                ).addMigrations(MIGRATION_1_2).build()
                INSTANCE = instance // Can't return INSTANCE because it is private
                return instance
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, `name` TEXT, " +
                        "PRIMARY KEY(`id`))")

            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE game_records ADD COLUMN group_mode TEXT NOT NULL DEFAULT ${GameRecord.GroupMode.SESSION}")
                database.execSQL("ALTER TABLE game_records ADD COLUMN game_type TEXT NOT NULL DEFAULT ${GameRecord.GameType.PRACTICE}")
                database.execSQL("ALTER TABLE game_records ADD COLUMN game_number INTEGER NOT NULL DEFAULT 0")
            }
        }
    }
}