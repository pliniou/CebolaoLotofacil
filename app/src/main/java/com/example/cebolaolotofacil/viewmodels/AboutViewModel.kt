package com.example.cebolaolotofacil.viewmodels

import androidx.lifecycle.ViewModel
import com.example.cebolaolotofacil.BuildConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Data class para organizar informações do aplicativo
 */
data class AppInfo(
    val appName: String = "",
    val versionName: String = "",
    val versionCode: Int = 0,
    val buildType: String = "",
    val isDebug: Boolean = false
)

class AboutViewModel : ViewModel() {

    // StateFlow para informações do app
    private val _appInfo = MutableStateFlow(AppInfo())
    val appInfo = _appInfo.asStateFlow()

    init {
        loadAppInfo()
    }

    /**
     * Carrega informações do aplicativo do BuildConfig
     */
    private fun loadAppInfo() {
        _appInfo.value = AppInfo(
            appName = "Cebolão Generator",
            versionName = BuildConfig.VERSION_NAME,
            versionCode = BuildConfig.VERSION_CODE,
            buildType = BuildConfig.BUILD_TYPE,
            isDebug = BuildConfig.DEBUG
        )
    }
}