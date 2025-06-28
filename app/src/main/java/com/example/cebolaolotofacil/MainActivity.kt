package com.example.cebolaolotofacil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.cebolaolotofacil.ui.screens.MainScreen
import com.example.cebolaolotofacil.ui.theme.CebolaoLotofacilTheme
import com.example.cebolaolotofacil.viewmodels.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        // Instala a Splash Screen
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var uiState: Boolean by mutableStateOf(true)

        // Observa o estado de carregamento do ViewModel
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.collect {
                    uiState = it
                }
            }
        }

        // Mantém a splash screen visível enquanto isLoading for true
        splashScreen.setKeepOnScreenCondition {
            uiState
        }

        setContent {
            CebolaoLotofacilTheme {
                MainScreen()
            }
        }
    }
}