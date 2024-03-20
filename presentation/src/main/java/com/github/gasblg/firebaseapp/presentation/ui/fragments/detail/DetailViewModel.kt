package com.github.gasblg.firebaseapp.presentation.ui.fragments.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.gasblg.firebaseapp.Logger
import com.github.gasblg.firebaseapp.analytics.crashlytics.CrashlyticsReporter
import com.github.gasblg.firebaseapp.domain.interactors.ItemsInteractor
import com.github.gasblg.firebaseapp.domain.managers.UserManager
import com.github.gasblg.firebaseapp.domain.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val itemsInteractor: ItemsInteractor,
    private val crashlytics: CrashlyticsReporter,
    private val userManager: UserManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private var user: UserProfile? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getUserInfo()
        }
    }

    private suspend fun getUserInfo() {
        user = userManager.getLocalUserData()
    }

    suspend fun getItem(itemId: String) {
        itemsInteractor.loadItem(itemId, user?.uid ?: "").collect { response ->
            when (response) {
                is Response.Loading -> _uiState.value = UiState.Loading
                is Response.Empty -> _uiState.value = UiState.Empty
                is Response.Failure -> {
                    crashlytics.error(Logger.TAG, response.e)
                    _uiState.value = UiState.Error(response.e)
                }

                is Response.Success -> _uiState.value = UiState.Success(response.data)
            }
        }
    }


    sealed class UiState {
        data object Loading : UiState()
        data object Empty : UiState()
        data class Success(val item: Item) : UiState()
        data class Error(val exception: Throwable) : UiState()
    }
}