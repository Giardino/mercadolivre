package com.lucianogiardino.mercadolivre.domain.usecase

import com.lucianogiardino.mercadolivre.data.repository.ProductRepository
import com.lucianogiardino.mercadolivre.domain.model.ProductModel
import javax.inject.Inject
class SearchProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(query: String): List<ProductModel> {
        return repository.findProducts(query)
    }
}