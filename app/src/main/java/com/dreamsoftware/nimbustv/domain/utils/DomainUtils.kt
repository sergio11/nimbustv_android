package com.dreamsoftware.nimbustv.domain.utils

import java.time.Duration
import java.time.LocalDateTime

fun isLiveNow(startTime: LocalDateTime, endTime: LocalDateTime): Boolean = with(LocalDateTime.now()) {
    isAfter(startTime) && isBefore(endTime)
}

fun calculateProgress(startTime: LocalDateTime, endTime: LocalDateTime) = with(LocalDateTime.now()) {
    val totalDuration = Duration.between(startTime, endTime).toMillis()
    val elapsedDuration = Duration.between(startTime, this).toMillis()
    if (totalDuration > 0) ((elapsedDuration * 100) / totalDuration).toInt() else 0
}