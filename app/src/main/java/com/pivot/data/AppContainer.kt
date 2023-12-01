package com.pivot.data

import android.content.Context

interface AppContainer{
    val articlesRepository: ArticlesRepository
    val settingsRepository: UserSettingsRepository
}

class AppDataContainer(private val context: Context,
) : AppContainer {
    override val articlesRepository: ArticlesRepository by lazy {
        OfflineArticlesRepository(PivotReaderDatabase.getDatabase(context).articleDao())
    }
    override val settingsRepository: UserSettingsRepository by lazy {
        OfflineUserSettingsRepository(PivotReaderDatabase.getDatabase(context).userSettingDao())
    }
}

