package com.mostafadevo.todotrackercompose.Utils

import com.mostafadevo.todotrackercompose.data.local.Priority
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

// Extension function to convert Priority to sort order
fun Priority.toSortOrder(): Int {
    return when (this) {
        Priority.HIGH -> 3
        Priority.MEDIUM -> 2
        Priority.LOW -> 1
        Priority.UNSPECIFIED -> 0
    }
}

// is used to display the date in the textfield of the add to-do dialog
fun Long.toFormattedDateString(
    format: String = "yyyy-MM-dd",
    zone: ZoneId = ZoneId.systemDefault()
): String {
    return Instant.ofEpochMilli(this)
        .atZone(zone)
        .format(DateTimeFormatter.ofPattern(format))
}

// combine the date as long and time as localtime to a single long value to store in the database
fun combineDateAndTime(
    dateMillis: Long,
    time: LocalTime,
    zone: ZoneId = ZoneId.systemDefault()
): Long {
    // Convert the date from Long to LocalDate
    val localDate = Instant.ofEpochMilli(dateMillis).atZone(zone).toLocalDate()

    // Combine LocalDate and LocalTime to get LocalDateTime
    val localDateTime = LocalDateTime.of(localDate, time)

    // Convert LocalDateTime to Instant, then to Long
    return localDateTime.atZone(zone).toInstant().toEpochMilli()
}

// extract the date from the combined value
fun Long.extractDate(zone: ZoneId = ZoneId.systemDefault()): Long {
    return Instant.ofEpochMilli(this).atZone(zone).toLocalDate().atStartOfDay(zone).toInstant()
        .toEpochMilli()
}

// extract the time from the combined value
fun Long.extractTime(zone: ZoneId = ZoneId.systemDefault()): LocalTime {
    return Instant.ofEpochMilli(this).atZone(zone).toLocalTime()
}

// convert local time to to long
fun LocalTime.toLong(zone: ZoneId = ZoneId.systemDefault()): Long {
    val localDate = LocalDateTime.of(1970, 1, 1, this.hour, this.minute)
    return localDate.atZone(zone).toInstant().toEpochMilli()
}
