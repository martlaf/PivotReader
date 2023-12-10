package com.pivot.data

import android.content.Context
import android.util.Log
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

        fun getDatabase(context: Context): PivotReaderDatabase =
            Instance ?: synchronized(this) {
                Instance ?: buildDatabase(context).also { Instance = it }
            }

        private fun buildDatabase(context: Context) =
                databaseBuilder(context.applicationContext,
                        PivotReaderDatabase::class.java, "pivot_reader_database")
                        // prepopulate the database on creation
                        .addCallback(object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                val scope = CoroutineScope(Dispatchers.IO)
                                Log.d("DB Callback", "Launching insert of default settings")
                                scope.launch {
                                    getDatabase(context).userSettingDao().insertSettings(DEFAULT_SETTINGS.values)
                                }
                            }
                        })
                        .fallbackToDestructiveMigration()
                        .build()
    }
}