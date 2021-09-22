package com.proway.testapp

import android.app.Application
import androidx.work.*
import com.google.firebase.FirebaseApp
import com.proway.testapp.work.PeriodicWorkManager
import java.util.*
import java.util.concurrent.TimeUnit

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(applicationContext)

        startWorker()
    }

    fun startWorker() {

        val workManager = WorkManager.getInstance(applicationContext)

        val constraints = Constraints.Builder()
            .setRequiresCharging(false)
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresBatteryNotLow(true)
            .build()

        val periodicWorkRequest = PeriodicWorkRequestBuilder<PeriodicWorkManager>(
            15,
            TimeUnit.MINUTES
        ).setConstraints(constraints).build()

        workManager.enqueueUniquePeriodicWork(
            UUID.randomUUID().toString(),
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )


    }

}