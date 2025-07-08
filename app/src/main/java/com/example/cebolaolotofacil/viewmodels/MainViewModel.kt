package com.example.cebolaolotofacil.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cebolaolotofacil.logic.HistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Representa os estados da UI durante a inicialização do app.
 */
sealed interface MainUiState {
    data object Loading : MainUiState
    data object Success : MainUiState
    data class Error(val message: String) : MainUiState
}

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        initializeApp()
    }

    /**
     * Inicializa a aplicação, focando na tarefa principal de carregar os dados históricos.
     * O estado da UI reflete o progresso: Loading, Success ou Error.
     */
    fun initializeApp() {
        _uiState.value = MainUiState.Loading
        viewModelScope.launch {
            try {
                // A tarefa mais longa é carregar o histórico.
                // A classe HistoryRepository já usa Dispatchers.IO internamente.
                HistoryRepository(getApplication()).getHistory()

                // Se a operação for bem-sucedida, o estado muda para Success.
                _uiState.value = MainUiState.Success

            } catch (exception: Exception) {
                // Em caso de qualquer falha, o estado muda para Error com uma mensagem.
                val errorMessage = when (exception) {
                    is java.io.FileNotFoundException -> "Erro: Arquivo de dados não encontrado."
                    else -> "Erro inesperado ao iniciar: ${exception.localizedMessage}"
                }
                _uiState.value = MainUiState.Error(errorMessage)
            }
        }
    }
}