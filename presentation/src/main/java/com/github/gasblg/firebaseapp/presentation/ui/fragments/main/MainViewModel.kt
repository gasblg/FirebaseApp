package com.github.gasblg.firebaseapp.presentation.ui.fragments.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.gasblg.firebaseapp.analytics.crashlytics.CrashlyticsReporter
import com.github.gasblg.firebaseapp.domain.interactors.ItemsInteractor
import com.github.gasblg.firebaseapp.domain.models.*
import com.github.gasblg.firebaseapp.domain.usecases.datastore.ClearPrefsUseCase
import com.github.gasblg.firebaseapp.domain.usecases.datastore.GetLocalUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainViewModel @Inject constructor(
    private val getLocalUserUseCase: GetLocalUserUseCase,
    private val clearPrefsUseCase: ClearPrefsUseCase,
    private val itemsInteractor: ItemsInteractor,
    private val crashlytics: CrashlyticsReporter
) : ViewModel() {

    private val _profileData = MutableStateFlow(UserProfile())
    val profileData = _profileData.asStateFlow()

    private val _uiState = MutableStateFlow<UiState>(UiState.Success(emptyList()))
    val uiState: StateFlow<UiState> = _uiState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getUserInfo()
            getItems()
        }
    }

    private suspend fun getUserInfo() {
        val user = getLocalUserUseCase.invoke()
        user?.let {
            _profileData.emit(it)
        }
    }

    private suspend fun getItems() {
        itemsInteractor.loadItems().collect { response ->
            when (response) {
                is Response.Loading -> _uiState.value = UiState.Loading
                is Response.Empty -> _uiState.value = UiState.Empty
                is Response.Failure -> {
                    crashlytics.error(MainFragment.TAG, response.e)
                    _uiState.value = UiState.Error(response.e)
                }
                is Response.Success -> _uiState.value = UiState.Success(response.data)
            }
        }
    }

    fun addItem(item: Item?) {
        viewModelScope.launch(Dispatchers.IO) {
            item?.let {
                itemsInteractor.addItem(it)
            }
        }
    }

    fun editItem(item: Item?) {
        viewModelScope.launch(Dispatchers.IO) {
            item?.let {
                itemsInteractor.editItem(it)
            }
        }
    }

    fun removeItem(itemId: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            itemsInteractor.removeItem(itemId)
        }
    }


    fun logOut() = viewModelScope.launch { clearPrefsUseCase.invoke() }


    sealed class UiState {
        data object Loading : UiState()
        data object Empty : UiState()
        data class Success(val items: List<Item>) : UiState()
        data class Error(val exception: Throwable) : UiState()
    }
}