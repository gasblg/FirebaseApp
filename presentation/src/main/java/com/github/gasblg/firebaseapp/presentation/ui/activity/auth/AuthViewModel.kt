package com.github.gasblg.firebaseapp.presentation.ui.activity.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.gasblg.firebaseapp.domain.managers.UserManager
import com.github.gasblg.firebaseapp.domain.models.UserProfile
import com.github.gasblg.firebaseapp.domain.usecases.date.DateConverterUseCase
import com.github.gasblg.firebaseapp.domain.usecases.user.SaveUserUseCase
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthViewModel @Inject constructor(
    private val userManager: UserManager,
    private val saveUserUseCase: SaveUserUseCase,
    private val dateConverterUseCase: DateConverterUseCase
) : ViewModel() {

    private val _saveProfile = MutableStateFlow(false)
    val saveProfile = _saveProfile.asStateFlow()

    fun setUser(user: FirebaseUser) {
        _saveProfile.value = false
        val profile = UserProfile(
            user.uid,
            user.displayName,
            user.email,
            user.photoUrl.toString(),
            dateConverterUseCase.invoke(user.metadata?.creationTimestamp),
            dateConverterUseCase.invoke(user.metadata?.lastSignInTimestamp)
        )

        saveUserUseCase.invoke(
            profile = profile,
            success = {
                viewModelScope.launch(Dispatchers.IO) { userManager.saveUser(profile) }
                _saveProfile.value = true
            }
        )
    }
}