package com.example.cebolaolotofacil.viewmodels

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cebolaolotofacil.logic.HistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _loadingProgress = MutableStateFlow(0f)
    val loadingProgress = _loadingProgress.asStateFlow()

    private val _loadingMessage = mutableStateOf("Iniciando Cebolão...")
    val loadingMessage: State<String> = _loadingMessage

    private val _hasError = mutableStateOf(false)
    val hasError: State<Boolean> = _hasError

    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String> = _errorMessage

    init {
        initializeApp()
    }

    /**
     * Inicializa a aplicação com carregamento progressivo.
     * As chamadas de delay foram removidas para otimizar o tempo de inicialização.
     */
    private fun initializeApp() {
        viewModelScope.launch {
            try {
                // Fase 1: Carregando recursos básicos
                _loadingMessage.value = "Carregando recursos..."
                _loadingProgress.value = 0.25f

                // Fase 2: Inicializando e carregando dados históricos (operação real de I/O)
                _loadingMessage.value = "Preparando dados históricos..."
                HistoryRepository(getApplication()).getHistory() // Operação principal
                _loadingProgress.value = 0.8f

                // Fase 3: Finalizando
                _loadingMessage.value = "Quase pronto..."
                _loadingProgress.value = 1.0f

                // Conclui o carregamento
                _isLoading.value = false
            } catch (exception: Exception) {
                handleLoadingError(exception)
            }
        }
    }

    /**
     * Gerencia erros durante o carregamento.
     */
    private fun handleLoadingError(exception: Exception) {
        _hasError.value = true
        _errorMessage.value = when (exception) {
            is java.lang.SecurityException -> "Erro de permissão. Verifique as configurações do app."
            is java.lang.OutOfMemoryError -> "Memória insuficiente. Feche outros aplicativos e tente novamente."
            else -> "Erro inesperado: ${exception.localizedMessage ?: "Desconhecido"}"
        }
        _isLoading.value = false
    }

    /**
     * Tenta reinicializar após erro.
     */
    fun retryInitialization() {
        _hasError.value = false
        _errorMessage.value = ""
        _isLoading.value = true
        _loadingProgress.value = 0f
        initializeApp()
    }

    /**
     * Força finalização do carregamento (para debug).
     */
    fun forceFinishLoading() {
        _isLoading.value = false
    }
}