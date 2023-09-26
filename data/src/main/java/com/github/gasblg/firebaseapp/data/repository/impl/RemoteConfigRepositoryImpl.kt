package com.github.gasblg.firebaseapp.data.repository.impl

import android.content.Context
import android.util.Log
import com.github.gasblg.firebaseapp.data.R
import com.github.gasblg.firebaseapp.domain.repository.RemoteConfigRepository
import com.github.gasblg.firebaseapp.domain.ConfigSettings
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteConfigRepositoryImpl @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig,
    private val context: Context
) : RemoteConfigRepository {

    override suspend fun init() {
        setConfig()
        fetchAndActivate()
    }

    private fun setConfig() {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);
    }

    private fun fetchAndActivate() {
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val updated = task.result.toString()
                Log.d(TAG, context.getString(R.string.config_params, updated))
                Log.d(TAG, context.getString(R.string.fetch_and_activate))
            } else {
                Log.d(TAG, context.getString(R.string.fetch_failed))
            }
        }
    }


    override fun getSettings(): Map<String, String> {
        return mapOf(ConfigSettings.TITLE to remoteConfig.getString(ConfigSettings.TITLE))
    }

    companion object {
        const val TAG = "RemoteConfigRepository"
    }
}