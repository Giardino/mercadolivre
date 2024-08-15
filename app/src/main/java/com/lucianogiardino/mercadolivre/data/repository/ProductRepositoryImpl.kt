package com.lucianogiardino.mercadolivre.data.repository

import com.lucianogiardino.mercadolivre.data.remote.ApiService
import com.lucianogiardino.mercadolivre.domain.model.ProductModel
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ProductRepository {
    override suspend fun findProducts(
        query: String
    ): List<ProductModel> {
        val response = apiService.searchProduct(query)
        if (response.isSuccessful) {
            response.body()?.let { findProductsResponse ->

                return findProductsResponse.results.map { resultDto ->
                    ProductModel(
                        id = resultDto.id,
                        name = resultDto.title,
                        price = resultDto.price,
                        thumbnail = ensureHttps(resultDto.thumbnail)
                    )
                }
            } ?: throw Exception("Empty response body")
        } else {
            throw Exception("Failed to fetch products: ${response.errorBody()?.string()}")
        }
    }

    private fun ensureHttps(url: String): String {
        return if (url.startsWith("http://")) {
            url.replaceFirst("http://", "https://")
        } else {
            url
        }
    }
}
