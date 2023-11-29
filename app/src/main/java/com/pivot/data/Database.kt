package com.pivot.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(entities=[ArticleItem::class, ArticleCategory::class], version=1, exportSchema = false)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile
        private var Instance: NewsDatabase? = null

        fun getDatabase(context: Context): NewsDatabase {
            return Instance ?: synchronized(this) {
                databaseBuilder(context, NewsDatabase::class.java, "news_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}