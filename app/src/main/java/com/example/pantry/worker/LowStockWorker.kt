package com.example.pantry.worker

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.pantry.PantryApplication

class LowStockWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val app = applicationContext as PantryApplication
        val items = app.repository.getShoppingList()
        if (items.isEmpty()) return Result.success()

        val granted = ContextCompat.checkSelfPermission(
            applicationContext, Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
        if (!granted) return Result.success()

        val names = items.joinToString(", ") { it.name }
        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("${items.size} Items to rebuy")
            .setContentText(names)
            .setStyle(NotificationCompat.BigTextStyle().bigText(names))
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(applicationContext).notify(1, notification)
        return Result.success()
    }

    companion object {
        const val CHANNEL_ID = "low_stock"
    }
}