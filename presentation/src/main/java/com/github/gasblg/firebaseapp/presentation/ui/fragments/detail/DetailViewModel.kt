package com.github.gasblg.firebaseapp.presentation.ui.fragments.detail

import androidx.lifecycle.ViewModel
import com.github.gasblg.firebaseapp.analytics.crashlytics.CrashlyticsReporter
import com.github.gasblg.firebaseapp.domain.interactors.ItemsInteractor
import com.github.gasblg.firebaseapp.domain.models.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailViewModel @Inject constructor(
    private val itemsInteractor: ItemsInteractor,
    private val crashlytics: CrashlyticsReporter
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState


    suspend fun getItem(itemId: String) {
        itemsInteractor.loadItem(itemId).collect { response ->
            when (response) {
                is Response.Loading -> _uiState.value = UiState.Loading
                is Response.Empty -> _uiState.value = UiState.Empty
                is Response.Failure -> {
                    crashlytics.error(DetailFragment.TAG, response.e)
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