package com.cebolao.lotofacil

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.cebolao.lotofacil.ui.screens.MainScreen
import com.cebolao.lotofacil.ui.theme.CebolaoLotofacilTheme
import com.cebolao.lotofacil.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }

            // REFINAMENTO: Animação de saída da splash screen mais suave e elegante.
            setOnExitAnimationListener { splashScreenViewProvider ->
                val splashView = splashScreenViewProvider.view
                val iconView = splashScreenViewProvider.iconView

                // Animação de fade out sutil da view da splash screen
                val splashFadeOut = ObjectAnimator.ofFloat(splashView, View.ALPHA, 1f, 0f).apply {
                    interpolator = LinearInterpolator() // Interpolação suave e linear
                    duration = 300L
                    doOnEnd { splashScreenViewProvider.remove() }
                }

                // Animação do ícone para encolher e desaparecer
                val iconScaleX = ObjectAnimator.ofFloat(iconView, View.SCALE_X, 1f, 0.5f)
                val iconScaleY = ObjectAnimator.ofFloat(iconView, View.SCALE_Y, 1f, 0.5f)
                val iconAlpha = ObjectAnimator.ofFloat(iconView, View.ALPHA, 1f, 0f)

                val iconAnimatorSet = AnimatorSet().apply {
                    interpolator = LinearInterpolator()
                    duration = 300L
                    playTogether(iconScaleX, iconScaleY, iconAlpha)
                }

                // Inicia as animações em conjunto para uma transição elegante
                AnimatorSet().apply {
                    playTogether(splashFadeOut, iconAnimatorSet)
                    start()
                }
            }
        }

        setContent {
            CebolaoLotofacilTheme {
                MainScreen()
            }
        }
    }
}