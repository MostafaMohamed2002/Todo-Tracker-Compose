package com.mostafadevo.todotrackercompose.Utils

import com.mostafadevo.todotrackercompose.data.local.Priority

// Extension function to convert Priority to sort order
fun Priority.toSortOrder(): Int {
    return when (this) {
        Priority.HIGH -> 3
        Priority.MEDIUM -> 2
        Priority.LOW -> 1
        Priority.UNSPECIFIED -> 0
    }
}