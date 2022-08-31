package com.example.reminder.service.worker_notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.reminder.R

class NotificationServiceWorker(private val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
//        get passed data
        val title = inputData.getString("title")
        val subtitle = inputData.getString("subtitle")
        val notificationId = inputData.getInt("notificationId", 0)


        val notificationManager = createNotificationChannel()
        val builder = NotificationCompat.Builder(context, "Reminder-App")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(subtitle)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        notificationManager.notify(notificationId, builder.build())

        return Result.success()
    }

    private fun createNotificationChannel(): NotificationManager {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name = "Channel-Reminder-App"
        val descriptionText = "Channel-Description"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("Reminder-App", name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        return notificationManager
    }
}
