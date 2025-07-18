package com.cebolao.lotofacil.logic

import android.content.Context
import com.cebolao.lotofacil.data.HistoricalDraw
import com.cebolao.lotofacil.data.network.ApiService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HistoryRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: ApiService
) {
    @Volatile
    private var historicalDrawsCache: List<HistoricalDraw>? = null
    private val lineRegex = """^\d+\s*-\s*[\d, ]+$""".toRegex()

    suspend fun getHistory(): List<HistoricalDraw> {
        // Retorna o cache imediatamente se já estiver disponível
        historicalDrawsCache?.let { return it }

        // CORREÇÃO: A chamada de rede/leitura de arquivo (que é demorada)
        // agora acontece fora do bloco 'synchronized' para evitar o aviso.
        val loadedHistory = loadHistory()

        // O bloco 'synchronized' agora é usado apenas para a operação rápida
        // de atribuir o valor à variável de cache.
        return synchronized(this) {
            historicalDrawsCache ?: loadedHistory.also {
                historicalDrawsCache = it
            }
        }
    }

    suspend fun getLastDraw(): HistoricalDraw? {
        return getHistory().firstOrNull()
    }

    private suspend fun loadHistory(): List<HistoricalDraw> = withContext(Dispatchers.IO) {
        val localHistory = parseHistoryFile()
        try {
            val apiResult = apiService.getLatestResult()
            val apiDraw = apiResult.concurso?.let { contest ->
                val numbers = apiResult.dezenas?.mapNotNull { it.toIntOrNull() }?.toSet()
                if (numbers != null && numbers.size >= 15) {
                    HistoricalDraw(contest, numbers)
                } else null
            }

            if (apiDraw != null && localHistory.none { it.contestNumber == apiDraw.contestNumber }) {
                (listOf(apiDraw) + localHistory).sortedByDescending { it.contestNumber }
            } else {
                localHistory
            }
        } catch (_: Exception) {
            localHistory
        }
    }

    private fun parseHistoryFile(): List<HistoricalDraw> {
        return try {
            context.assets.open("lotofacil_resultados.txt").bufferedReader().useLines { lines ->
                lines
                    .filter { it.isNotBlank() && it.matches(lineRegex) }
                    .mapNotNull { parseLine(it) }
                    .sortedByDescending { it.contestNumber }
                    .toList()
            }
        } catch (_: IOException) {
            // Em caso de erro, retorna uma lista vazia para não quebrar o app
            emptyList()
        }
    }

    private fun parseLine(line: String): HistoricalDraw? {
        return try {
            val parts = line.split(" - ", limit = 2)
            val contestNumber = parts[0].trim().toInt()
            val numbers = parts[1].split(",").map { it.trim().toInt() }.toSet()
            if (numbers.size < 15) null else HistoricalDraw(contestNumber, numbers)
        } catch (_: Exception) {
            null
        }
    }
}