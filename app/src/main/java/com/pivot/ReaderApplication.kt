package com.pivot

import android.app.Application
import com.pivot.data.AppContainer
import com.pivot.data.AppDataContainer

class ReaderApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
