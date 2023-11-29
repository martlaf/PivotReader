package com.pivot.data


interface ArticlesRepository {
    fun getAllArticles(): List<ArticleItem>
    fun getArticle(id: Int): ArticleItem?
    suspend fun insertArticle(article: ArticleItem)
}