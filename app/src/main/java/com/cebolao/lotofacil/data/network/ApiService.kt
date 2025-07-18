package com.cebolao.lotofacil.data.network

import retrofit2.http.GET

interface ApiService {
    @GET("latest")
    suspend fun getLatestResult(): LotofacilApiResult
}