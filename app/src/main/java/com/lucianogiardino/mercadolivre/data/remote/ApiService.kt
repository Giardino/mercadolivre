package com.lucianogiardino.mercadolivre.data.remote

import com.lucianogiardino.mercadolivre.data.model.FindProductsResponse
import com.lucianogiardino.mercadolivre.data.model.ProductDescriptionResponse
import com.lucianogiardino.mercadolivre.data.model.ProductDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/sites/MLB/search")
    suspend fun searchProduct(
        @Query("q") query:String
    ): Response<FindProductsResponse>

    @GET("items/{itemId}")
    suspend fun getProductDetail(
        @Path("itemId") itemId: String
    ): Response<ProductDetailResponse>
    @GET("items/{itemId}/description")
    suspend fun getProductDescription(
        @Path("itemId") itemId: String
    ): Response<ProductDescriptionResponse>
}