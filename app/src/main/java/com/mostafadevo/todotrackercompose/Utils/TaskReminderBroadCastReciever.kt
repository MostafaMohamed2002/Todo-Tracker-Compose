package com.mostafadevo.todotrackercompose.Utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.mostafadevo.todotrackercompose.R
import com.mostafadevo.todotrackercompose.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class TaskReminderBroadCastReciever : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val taskID = intent.getIntExtra("TASK_ID", 0)
        val taskTitle = intent.getStringExtra("TASK_TITLE")
        val taskDescription = intent.getStringExtra("TASK_DESCRIPTION")
        Timber.d("TaskReminderBroadCastReciever -> onReceive: Task ID: $taskID")
        sendNotification(context, taskID, taskTitle, taskDescription)
    }

    private fun sendNotification(
        context: Context, taskID: Int, taskTitle: String?, taskDescription: String?
    ) {
        val notificationIntent = Intent(context, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            taskID,
            notificationIntent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        // mark as completed intent
        val markAsCompletedIntent =
            Intent(context, OnTaskMarkedAsCompletedReciever::class.java).apply {
                putExtra("TASK_ID", taskID)
            }
        val markAsCompletedPendingIntent = PendingIntent.getBroadcast(
            context,
            taskID,
            markAsCompletedIntent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(
            context, "todo_channel"
        ).setSmallIcon(R.mipmap.ic_launcher).setContentTitle(taskTitle)
            .setContentText(taskDescription).setAutoCancel(true)
            .setContentIntent(pendingIntent).setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            .addAction(
                R.drawable.baseline_done_24,
                "Completed",
                markAsCompletedPendingIntent
            )
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(taskID, notificationBuilder.build())
        Timber.d("TaskReminderBroadCastReciever -> sendNotification: Task ID: $taskID")
    }
}