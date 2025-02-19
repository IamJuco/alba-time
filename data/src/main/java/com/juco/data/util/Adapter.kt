package com.juco.data.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

val gson: Gson = GsonBuilder()
    .registerTypeAdapter(LocalTime::class.java, LocalTimeAdapter())
    .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
    .create()

class LocalTimeAdapter : JsonSerializer<LocalTime>, JsonDeserializer<LocalTime> {
    override fun serialize(
        src: LocalTime?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src?.format(DateTimeFormatter.ofPattern("HH:mm")))
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalTime {
        return runCatching {
            LocalTime.parse(json?.asString, DateTimeFormatter.ofPattern("HH:mm"))
        }.getOrElse {
            LocalTime.of(0, 0)
        }
    }
}

class LocalDateAdapter : JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
    override fun serialize(
        src: LocalDate?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src?.format(DateTimeFormatter.ISO_LOCAL_DATE))
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDate {
        return runCatching {
            LocalDate.parse(json?.asString, DateTimeFormatter.ISO_LOCAL_DATE)
        }.getOrElse {
            LocalDate.of(1970, 1, 1)
        }
    }
}