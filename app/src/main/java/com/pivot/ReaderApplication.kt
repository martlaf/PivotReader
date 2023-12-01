package com.pivot

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.pivot.data.AppContainer
import com.pivot.data.AppDataContainer
import com.pivot.reader.R
import com.pivot.work.ArticleFetcherWorker
import java.time.Duration


class ReaderApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()

        container = AppDataContainer(this)

        registerNotificationChannel()
        scheduleArticleFetcher()
    }

    private fun scheduleArticleFetcher() {
        val workManager = WorkManager.getInstance(this)
        val fetcherWorkRequestBuilder = PeriodicWorkRequestBuilder<ArticleFetcherWorker>(repeatInterval= Duration.ofHours(1))

        workManager.enqueue(fetcherWorkRequestBuilder.build())

    }

    private fun registerNotificationChannel() {
        val name = getString(R.string.channel_name)
        val descriptionText = getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }

    companion object {
        const val CHANNEL_ID = "pivot_channel"
    }
}
