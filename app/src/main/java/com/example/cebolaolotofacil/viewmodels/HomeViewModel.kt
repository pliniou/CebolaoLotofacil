package com.example.cebolaolotofacil.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * ViewModel para a HomeScreen.
 * Gerencia o estado da tela principal, incluindo as dicas rotativas e as animações de introdução.
 */
class HomeViewModel : ViewModel() {

    // Gerencia o estado para a animação de introdução "run-once".
    private val _isIntroAnimationEnabled = mutableStateOf(true)
    val isIntroAnimationEnabled: State<Boolean> = _isIntroAnimationEnabled

    private val _currentTipIndex = mutableStateOf(0)

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
        // Desabilita a animação de introdução após um tempo para que ela não rode novamente.
        disableIntroAnimationAfterDelay()
    }

    /**
     * Inicia a rotação automática das dicas.
     */
    private fun startTipRotation() {
        viewModelScope.launch {
            while (true) {
                delay(8000) // Troca a dica a cada 8 segundos
                _currentTipIndex.value = (_currentTipIndex.value + 1) % tips.size
            }
        }
    }

    /**
     * Desabilita as animações de entrada após um delay para que rodem apenas na primeira vez
     * que a tela é exibida, e não em recomposições ou mudanças de configuração.
     */
    private fun disableIntroAnimationAfterDelay() {
        viewModelScope.launch {
            delay(3000) // Tempo suficiente para as animações de entrada terminarem.
            _isIntroAnimationEnabled.value = false
        }
    }

    /**
     * Obtém a dica atual a ser exibida.
     */
    fun getCurrentTip(): String = tips[_currentTipIndex.value]

    /**
     * Avança para a próxima dica, geralmente acionado por um clique do usuário.
     */
    fun nextTip() {
        _currentTipIndex.value = (_currentTipIndex.value + 1) % tips.size
    }
}