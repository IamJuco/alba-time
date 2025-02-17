package com.juco.feature.calendar.util

import com.github.fj.koreanlunarcalendar.KoreanLunarDate
import java.time.LocalDate

// KoreanLunarDate 라이브러리 변환기
fun KoreanLunarDate.toLocalDate(): LocalDate? {
    return try {
        LocalDate.of(this.solYear, this.solMonth, this.solDay)
    } catch (e: Exception) {
        // catch가 아니라 다른걸로 할까?
        null
    }
}