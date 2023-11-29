package com.pivot.data


class OfflineArticlesRepository(private val articleDao: ArticleDao) : ArticlesRepository {
    override fun getAllArticles(): List<ArticleItem> = articleDao.getAllArticles()
    override fun getArticle(id: Int): ArticleItem = articleDao.getArticle(id)
    override suspend fun insertArticle(article: ArticleItem) = articleDao.insertArticle(article)
}
