package com.pivot.work

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.pivot.ReaderApplication
import com.pivot.reader.R

class NotifierWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    @SuppressLint("MissingPermission")
    override fun doWork(): Result {
        val articleId = inputData.getInt(idKey, -1)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("app://pivot.quebec/?p=$articleId")).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

//        val deepLinkIntent = NavDeepLinkBuilder(applicationContext)
//            .setGraph()
//            .setDestination("article/$articleId")
//            .createPendingIntent()
//            .setGraph(R.navigation.nav_graph)
//            .setDestination(R.id.articleScreen)
//            .setArguments(
//                ArticleScreenArgs.Builder(articleId).build().toBundle()
//            )
//            .createPendingIntent()


//        val body = "Article $articleId came out!"
        val notificationTitle = "Pivot"
        val builder = NotificationCompat.Builder(applicationContext, ReaderApplication.CHANNEL_ID)
            .setSmallIcon(R.drawable.logotype_principal_foreground)
            .setContentTitle(notificationTitle)
            .setContentText(inputData.getString(titleKey))
            .setStyle(NotificationCompat.BigTextStyle().bigText(inputData.getString(titleKey)))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(applicationContext)) {
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Log.d("NotifierWorker", "NO PERMISSIONS")
                return Result.failure()
            }
            Log.d("NotifierWorker", "Notifying")
            notify(articleId, builder.build())
        }

        return Result.success()
    }

    companion object {
        const val idKey = "ID"
        const val titleKey = "TITLE"
    }
}
