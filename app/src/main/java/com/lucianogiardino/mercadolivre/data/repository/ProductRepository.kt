package com.lucianogiardino.mercadolivre.data.repository

import com.lucianogiardino.mercadolivre.domain.model.ProductModel

interface ProductRepository {
    suspend fun findProducts(query: String): List<ProductModel>
}