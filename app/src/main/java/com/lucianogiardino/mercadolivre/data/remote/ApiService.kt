package com.lucianogiardino.mercadolivre.data.remote

import com.lucianogiardino.mercadolivre.data.model.FindProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/sites/MLB/search")
    suspend fun searchProduct(@Query("q") query:String): Response<FindProductsResponse>
}