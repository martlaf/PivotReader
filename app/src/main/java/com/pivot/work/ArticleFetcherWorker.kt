package com.pivot.work

import android.annotation.SuppressLint
import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.pivot.base.BaseApplication
import com.pivot.rss.RssClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ArticleFetcherWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    @SuppressLint("MissingPermission")
    override fun doWork(): Result {
        val application = applicationContext as BaseApplication
        val articlesRepository = application.container.articlesRepository

        val workManager = WorkManager.getInstance(application)
        val myWorkRequestBuilder = OneTimeWorkRequestBuilder<NotifierWorker>()

        coroutineScope.launch {
            val newArticlesList = RssClient().fetchRssData()
            val knownArticlesList = articlesRepository.getAllArticles()
            val unknownArticlesList = newArticlesList.subtract(knownArticlesList.toSet())

            for (article in unknownArticlesList) {
                myWorkRequestBuilder.setInputData(
                    workDataOf(
                        "ID" to article.id,
                        "TITLE" to article.title
                    )
                )
                workManager.enqueue(myWorkRequestBuilder.build())

                articlesRepository.insertArticle(article)
            }
        }

        return Result.success()
    }
}
