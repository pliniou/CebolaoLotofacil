package com.example.cebolaolotofacil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.cebolaolotofacil.ui.screens.MainScreen
import com.example.cebolaolotofacil.ui.theme.CebolaoLotofacilTheme
import com.example.cebolaolotofacil.viewmodels.MainUiState
import com.example.cebolaolotofacil.viewmodels.MainViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        // Instala e configura a Splash Screen.
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        // Mantém a splash screen visível enquanto o ViewModel estiver no estado de carregamento.
        // A condição observa o StateFlow de forma segura.
        splashScreen.setKeepOnScreenCondition {
            viewModel.uiState.value is MainUiState.Loading
        }

        setContent {
            CebolaoLotofacilTheme {
                MainScreen()
            }
        }
    }
}