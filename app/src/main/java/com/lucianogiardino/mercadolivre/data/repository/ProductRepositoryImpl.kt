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
                        originalPrice = resultDto.originalPrice ?: 0.0,
                        description = "",
                        pictures = listOf(),
                        thumbnail = ensureHttps(resultDto.thumbnail)
                    )
                }
            } ?: throw Exception("Empty response body")
        } else {
            throw Exception("Failed to fetch products: ${response.errorBody()?.string()}")
        }
    }

    override suspend fun getProductDetail(productId: String): ProductModel {
        val response = apiService.getProductDetail(productId)
        if (response.isSuccessful) {
            response.body()?.let { productDetailResponse ->

                return ProductModel(
                        id = productDetailResponse.id,
                        name = productDetailResponse.title,
                        price = productDetailResponse.price,
                        originalPrice = productDetailResponse.originalPrice ?: 0.0,
                        description = "",
                        pictures = productDetailResponse.pictures.map { pictureDto ->
                            pictureDto.secureUrl
                        },
                        thumbnail = ensureHttps(productDetailResponse.thumbnail)
                    )

            } ?: throw Exception("Empty response body")
        } else {
            throw Exception("Failed to fetch products detail: ${response.errorBody()?.string()}")
        }
    }

    override suspend fun getProductDescription(productId: String): String {
        val response = apiService.getProductDescription(productId)
        if (response.isSuccessful) {
            response.body()?.let { productDescriptionResponse ->
                return productDescriptionResponse.plainText
            } ?: throw Exception("Empty response body")
        } else {
            throw Exception("Failed to fetch products description: ${response.errorBody()?.string()}")
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
