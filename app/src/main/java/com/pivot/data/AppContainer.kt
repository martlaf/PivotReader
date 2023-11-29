package com.pivot.data

import android.content.Context

interface AppContainer{
    val articlesRepository: ArticlesRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val articlesRepository: ArticlesRepository by lazy {
        OfflineArticlesRepository(NewsDatabase.getDatabase(context).articleDao())
    }
}

