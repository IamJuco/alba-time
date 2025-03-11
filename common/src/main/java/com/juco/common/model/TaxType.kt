package com.juco.common.model

enum class UiTaxType(val rate: Float, val displayName: String) {
    NONE(0.0f, "없음"),
    LOW(3.3f, "3.3%"),
    HIGH(9.4f, "9.4%");
}