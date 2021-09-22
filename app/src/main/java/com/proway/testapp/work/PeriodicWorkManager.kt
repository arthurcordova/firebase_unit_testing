package com.proway.testapp.work

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.*

class PeriodicWorkManager(val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    private val notificationHandler: NotificationHandler = NotificationHandler(context)

    override fun doWork(): Result {
        val date = Calendar.getInstance()


        val stringDate = "$${date[Calendar.MINUTE]}:${date[Calendar.SECOND]}"

        notificationHandler.createNotification(
            "Workmanager in progress",
            "Has been executed by workmanager $stringDate"
        ).let { notification -> NotificationManagerCompat.from(context).notify((0..999).random(), notification) }
        return Result.success()
    }

}