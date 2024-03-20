package com.github.gasblg.firebaseapp.data.serializer

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.github.gasblg.firebaseapp.User

import java.io.InputStream
import java.io.OutputStream

object UserSerializer: Serializer<User> {
    override val defaultValue: User
        get() = User.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): User {
        return User.parseFrom(input)
    }

    override suspend fun writeTo(t: User, output: OutputStream) {
        return t.writeTo(output)
    }
}


val Context.userDataStore: DataStore<User> by dataStore(
    fileName = "user.pb",
    serializer = UserSerializer
)