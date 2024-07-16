package com.mostafadevo.todotrackercompose.Utils

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mostafadevo.todotrackercompose.domain.usecase.MarkTodoAsCompletedUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class OnTaskMarkedAsCompletedReciever : BroadcastReceiver() {
    @Inject
    lateinit var useCase: MarkTodoAsCompletedUseCase
    override fun onReceive(context: Context?, intent: Intent?) {
        // Handle the task marked as completed
        val taskId = intent?.getIntExtra("TASK_ID", 0)
        // Update the task in the database with thread
        CoroutineScope(Dispatchers.IO).launch {
            useCase.invoke(taskId!!)
            withContext(Dispatchers.Main) {
                val notificationManager =
                    context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.cancel(taskId!!)
            }
        }
    }
}