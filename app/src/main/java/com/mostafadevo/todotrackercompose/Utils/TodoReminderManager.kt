package com.mostafadevo.todotrackercompose.Utils

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.mostafadevo.todotrackercompose.data.local.Todo
import timber.log.Timber

/**
 * This class is responsible for setting up reminders for the Todo tasks.
 *
 * @property context The application context.
 */
class TodoReminderManager(
    private val context: Context
) {
    /**
     * This function sets a reminder for a specific Todo task.
     * It creates an alarm that triggers at the task's due date and time.
     * The alarm intent contains the task's id, title, and description as extras.
     *
     * @param todo The Todo task for which the reminder is to be set.
     */
    @SuppressLint("ScheduleExactAlarm")
    fun setTodoReminder(todo: Todo) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                // Log an error or inform the user
                Timber.d("TaskReminderManager -> Exact alarms not allowed")
                return
            }
        }
        // Get the AlarmManager service
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Create an intent for the MainActivity and add the task's details as extras
        val intent = Intent(context, TaskReminderBroadCastReciever::class.java).apply {
            putExtra("TASK_ID", todo.id)
            putExtra("TASK_TITLE", todo.title)
            putExtra("TASK_DESCRIPTION", todo.description)
        }

        // Create a PendingIntent that will broadcast the intent
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            todo.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Set the alarm to trigger at the task's due date and time
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            todo.dueDateTime,
            pendingIntent
        )
        Timber.d("Reminder set for task: ${todo.id} at ${todo.dueDateTime.extractTime()}")
    }
}