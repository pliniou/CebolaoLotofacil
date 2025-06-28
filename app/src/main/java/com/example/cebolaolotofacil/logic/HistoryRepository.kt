package com.example.cebolaolotofacil.logic

import android.content.Context
import com.example.cebolaolotofacil.data.HistoricalDraw
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HistoryRepository(private val context: Context) {

    private var historicalDraws: List<HistoricalDraw>? = null
    private var lastContestNumber: Int = 0

    suspend fun getHistory(): List<HistoricalDraw> {
        if (historicalDraws != null) {
            return historicalDraws!!
        }

        return withContext(Dispatchers.IO) {
            val draws = mutableListOf<HistoricalDraw>()
            // Regex para validar se a linha começa com um número, seguido por " - "
            val lineRegex = """^\d+\s*-\s*.*$""".toRegex()

            try {
                context.assets.open("lotofacil-resultados-1-3421.txt").bufferedReader().useLines { lines ->
                    lines.forEach { line ->
                        // SÓ PROCESSA A LINHA SE ELA CORRESPONDER AO FORMATO ESPERADO
                        if (line.isNotBlank() && line.matches(lineRegex)) {
                            try {
                                val parts = line.split(" - ")
                                val contestNumber = parts[0].trim().toInt()
                                val numbers = parts[1].split(",").map { it.trim().toInt() }.toSet()
                                draws.add(HistoricalDraw(contestNumber, numbers))
                            } catch (e: Exception) {
                                // Ignora linhas mal formatadas dentro do loop, para não parar todo o processo
                                println("Linha mal formatada ignorada: $line")
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (draws.isNotEmpty()) {
                lastContestNumber = draws.first().contestNumber
            }

            historicalDraws = draws
            draws
        }
    }

    fun getLastContestNumber(): Int {
        return lastContestNumber
    }
}