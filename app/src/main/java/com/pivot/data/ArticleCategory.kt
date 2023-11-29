package com.pivot.data

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(tableName = "categories")
data class ArticleCategory (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val categoryName: String,
)

@Dao
interface CategoryDao {
    @Query("select categoryName from categories where id = :id")
    fun getName(id: Int): String

    @Query("select id from categories where categoryName = :name")
    fun getId(name: String): Int
}
