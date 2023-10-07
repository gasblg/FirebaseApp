package com.github.gasblg.firebaseapp.domain.usecases.date

import com.github.gasblg.firebaseapp.domain.helpers.DateManager

class DateConverterUseCase(private val dateManager: DateManager) {

    fun invoke(time: Long?) = dateManager.convertLongToTime(time)
}