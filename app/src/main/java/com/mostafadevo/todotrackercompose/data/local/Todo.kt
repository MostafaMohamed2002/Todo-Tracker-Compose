package com.mostafadevo.todotrackercompose.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class Todo(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String?,
    val priority: Priority,
    val isCompleted: Boolean,
    val isAlarmEnabled: Boolean,
    val dueDateTime: Long,
)

enum class Priority {
    LOW, MEDIUM, HIGH, UNSPECIFIED
}