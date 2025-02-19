package com.juco.feature.calendar.util

import com.juco.domain.model.WorkPlace
import java.time.Duration

fun WorkPlace.workTimeCalculator(): Long {
    val duration = Duration.between(this.workTime.workStartTime, this.workTime.workEndTime)
    return duration.toHours()
}
