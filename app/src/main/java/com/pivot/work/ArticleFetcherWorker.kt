package com.pivot.work

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.pivot.ReaderApplication
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
        val application = applicationContext as ReaderApplication
        val articlesRepository = application.container.articlesRepository

        val workManager = WorkManager.getInstance(application)
        val myWorkRequestBuilder = OneTimeWorkRequestBuilder<NotifierWorker>()

        coroutineScope.launch {
            val newArticlesList = RssClient().fetchRssData()
            val knownIds = articlesRepository.getAllArticles().map { it.id }.toSet()
            val unknownArticles = newArticlesList.filterNot { it.id in knownIds }

            for (id in knownIds) {
                Log.d("ArticleFetcher", "Already know article $id")
            }

            for (article in unknownArticles) {
                Log.d("ArticleFetcher", "Got new article ${article.id}")
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
