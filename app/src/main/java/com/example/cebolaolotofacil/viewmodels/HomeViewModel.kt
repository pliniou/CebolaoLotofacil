package com.example.cebolaolotofacil.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * ViewModel para a HomeScreen.
 * Gerencia animações e estado da tela principal.
 */
class HomeViewModel : ViewModel() {

    private val _isCardsAnimationEnabled = mutableStateOf(true)
    val isCardsAnimationEnabled: State<Boolean> = _isCardsAnimationEnabled

    private val _currentTipIndex = mutableStateOf(0)
    val currentTipIndex: State<Int> = _currentTipIndex

    private val _showWelcomeAnimation = mutableStateOf(true)
    val showWelcomeAnimation: State<Boolean> = _showWelcomeAnimation

    // Dicas rotativas do Cebolão
    private val tips = listOf(
        "Comece com poucos filtros e intervalos amplos para ter mais opções de jogos.",
        "Analise os padrões históricos, mas lembre-se: cada sorteio é independente.",
        "Use filtros estatísticos como guia, não como garantia de prêmios.",
        "Diversifique suas apostas em vez de concentrar tudo em um padrão.",
        "O equilíbrio entre pares e ímpares costuma ser mais eficiente.",
        "Números da moldura tendem a aparecer com mais frequência nos sorteios."
    )

    init {
        startTipRotation()
        disableWelcomeAnimationAfterDelay()
    }

    /**
     * Inicia rotação automática das dicas.
     */
    private fun startTipRotation() {
        viewModelScope.launch {
            while (true) {
                delay(8000) // Troca dica a cada 8 segundos
                _currentTipIndex.value = (_currentTipIndex.value + 1) % tips.size
            }
        }
    }

    /**
     * Desabilita animação de boas-vindas após tempo determinado.
     */
    private fun disableWelcomeAnimationAfterDelay() {
        viewModelScope.launch {
            delay(3000)
            _showWelcomeAnimation.value = false
        }
    }

    /**
     * Obtém a dica atual.
     */
    fun getCurrentTip(): String = tips[_currentTipIndex.value]

    /**
     * Avança para próxima dica manualmente.
     */
    fun nextTip() {
        _currentTipIndex.value = (_currentTipIndex.value + 1) % tips.size
    }

    /**
     * Controla exibição das animações dos cards.
     */
    fun toggleCardsAnimation() {
        _isCardsAnimationEnabled.value = !_isCardsAnimationEnabled.value
    }
}