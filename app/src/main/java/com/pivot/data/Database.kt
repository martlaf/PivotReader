package com.pivot.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities=[ArticleItem::class, UserSetting::class], version=1, exportSchema = false)
abstract class PivotReaderDatabase: RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun userSettingDao(): UserSettingDao

    companion object {
        @Volatile
        private var Instance: PivotReaderDatabase? = null
        private val coroutineScope = CoroutineScope(Dispatchers.IO)

        fun getDatabase(context: Context): PivotReaderDatabase =
            Instance ?: synchronized(this) {
                Instance ?: buildDatabase(context).also { Instance = it }
            }

        private fun buildDatabase(context: Context) =
                databaseBuilder(context.applicationContext,
                        PivotReaderDatabase::class.java, "pivot_reader_database")
                        // prepopulate the database after onCreate was called
                        .addCallback(object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                // insert the data on the IO Thread
                                coroutineScope.launch {
                                    getDatabase(context).userSettingDao().insertSettings(DEFAULT_SETTINGS)
                                }
                            }
                        })
                        .fallbackToDestructiveMigration()
                        .build()
    }
}