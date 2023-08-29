package com.olgag.currencyconverter.api_service

import android.content.Context
import androidx.compose.animation.core.MutableTransitionState
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.olgag.currencyconverter.API_KEY

interface OnFBConfigListener {
    fun setApiKey(loader: MutableTransitionState<Boolean>, error: Boolean)
}
data class FBHelper(val context: Context)  {
    private val listener: OnFBConfigListener = context as OnFBConfigListener

    fun setApiKey(loader: MutableTransitionState<Boolean>, error: Boolean): Boolean {
        try {
            val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
            val configSettings = remoteConfigSettings {
                minimumFetchIntervalInSeconds = 600
            }
            remoteConfig.setConfigSettingsAsync(configSettings)
            remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    API_KEY = remoteConfig.getString("key_api")
                    listener.setApiKey(loader, false)
                   // Log.d("TAG", "Remote config value: $API_KEY")
                } else {
                   // Log.i("res: ", "Fetching and activating Remote Config values failed")
                    listener.setApiKey(loader, true)
                }
            }
            return true
        } catch (e: Exception) {
           // Log.i("res: ", e.toString())
            listener.setApiKey(loader, true)
            return false
        }
    }
}