package com.juco.feature.calendar.util

import com.juco.domain.model.WorkPlace
import java.time.Duration

fun WorkPlace.workTimeCalculator(): String {
    val duration = Duration.between(this.workTime.workStartTime, this.workTime.workEndTime)
    val totalMinutes = duration.toMinutes() - this.breakTime
    val hours = totalMinutes / 60
    val minutes = totalMinutes % 60

    return when {
        minutes == 0L -> "${hours}시간"
        hours == 0L -> "${minutes}분"
        else -> "${hours}시간 ${minutes}분"
    }
}