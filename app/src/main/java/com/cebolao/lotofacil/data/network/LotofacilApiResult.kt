package com.cebolao.lotofacil.data.network

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class LotofacilApiResult(
    val concurso: Int? = null,
    val dezenas: List<String>? = null
)