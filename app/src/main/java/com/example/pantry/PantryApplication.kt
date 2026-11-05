package com.example.pantry

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.pantry.data.AppDatabase
import com.example.pantry.data.PantryRepository
import com.example.pantry.worker.LowStockWorker
import java.util.concurrent.TimeUnit

class PantryApplication : Application() {

    val repository: PantryRepository by lazy {
        PantryRepository(AppDatabase.get(this).pantryDao())
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        scheduleLowStockCheck()


        WorkManager.getInstance(this).enqueue(
            androidx.work.OneTimeWorkRequestBuilder<LowStockWorker>().build()
        )
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                LowStockWorker.CHANNEL_ID,
                "Low Stock",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            getSystemService(NotificationManager::class.java)
                .createNotificationChannel(channel)
        }
    }

    private fun scheduleLowStockCheck() {
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val request = PeriodicWorkRequestBuilder<LowStockWorker>(12, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "low_stock_check",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }
}