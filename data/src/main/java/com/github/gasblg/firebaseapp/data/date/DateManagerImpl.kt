package com.github.gasblg.firebaseapp.data.date

import com.github.gasblg.firebaseapp.domain.helpers.DateManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateManagerImpl : DateManager {

    companion object {
        private const val DATE_PATTERN = "yyyy.MM.dd"
    }

    private val timeFormat = SimpleDateFormat(DATE_PATTERN, Locale.getDefault())

    override fun convertLongToTime(time: Long?): String {
        time?.let {
            val date = Date(it)
            return timeFormat.format(date)
        }
        return ""
    }

}