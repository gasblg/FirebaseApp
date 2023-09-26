package com.github.gasblg.firebaseapp.data.repository.impl

import com.github.gasblg.firebaseapp.data.utils.Constants
import com.github.gasblg.firebaseapp.domain.models.UserProfile
import com.github.gasblg.firebaseapp.domain.repository.UserRepository
import com.google.firebase.database.FirebaseDatabase

class UserRepositoryImpl(instance: FirebaseDatabase) : UserRepository {

    private val userRef = instance.getReference(Constants.USERS)

    override fun saveUser(profile: UserProfile, success: () -> Unit) {
        userRef.child(profile.uid.toString())
            .setValue(profile) { _, _ ->
                success.invoke()
            }
    }

}