package com.pivot.data
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(tableName = "articles")
data class ArticleItem (
    @PrimaryKey
    val id: Int = 0,
    val title: String,
    val imageUrl: String,
    val link: String,
    val author: String,
//    val categories: List<Int>? = null,
    val content: String,
    val date: String,
)



@Dao
interface ArticleDao {
    @Query("select * from articles")
    fun getAllArticles(): List<ArticleItem>

    @Query("select * from articles where id = :id")
    fun getArticle(id: Int): ArticleItem

    @Insert(onConflict=OnConflictStrategy.IGNORE)
    suspend fun insertArticle(article: ArticleItem)
}