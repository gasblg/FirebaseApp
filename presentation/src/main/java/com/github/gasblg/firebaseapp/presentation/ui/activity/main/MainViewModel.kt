package com.github.gasblg.firebaseapp.presentation.ui.activity.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.gasblg.firebaseapp.domain.ConfigSettings
import com.github.gasblg.firebaseapp.domain.usecases.config.GetConfigSettingsUseCase
import com.github.gasblg.firebaseapp.domain.usecases.config.InitConfigSettingsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainViewModel @Inject constructor(
    initConfigSettingsUseCase: InitConfigSettingsUseCase,
    private val getConfigSettingsUseCase: GetConfigSettingsUseCase
) : ViewModel() {

    private val _titleData = MutableStateFlow("")
    val titleData = _titleData.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            initConfigSettingsUseCase.invoke()
        }
        getSettings()
    }

    private fun getSettings() {
        val settings = getConfigSettingsUseCase.invoke()
        _titleData.value = settings[ConfigSettings.TITLE] ?: ""
    }
}