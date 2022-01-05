package com.onfido.techtask.cat.data.model

import android.util.Log
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@Serializable
data class CatFact(
    @SerialName("status") val status: Status,
    @SerialName("text") val text: String,
    @SerialName("createdAt") val createdAt: String
)

const val CAT_FACT_DATE_FORMAT: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

const val IS_NEW_MAX_DAYS: Int = 90

fun CatFact.isNew(nowMillis: Long, createdAtMillis: Long): Boolean {
    val diff: Long = nowMillis - createdAtMillis
    val days = TimeUnit.MILLISECONDS.toDays(diff)
    return days < IS_NEW_MAX_DAYS
}

fun toMillis(date: String, dateFormat: String): Long {
    val defaultMillis = 0L
    val millis: Long? = try {
        SimpleDateFormat(dateFormat, Locale.getDefault()).parse(date)?.time
    } catch (e: ParseException) {
        Log.e("[ONFIDO_TECH_TASK]", "failed parsing createdAt: $date")
        defaultMillis
    }
    return millis ?: defaultMillis
}