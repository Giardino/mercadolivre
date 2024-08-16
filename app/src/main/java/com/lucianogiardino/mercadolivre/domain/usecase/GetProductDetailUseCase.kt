package com.lucianogiardino.mercadolivre.domain.usecase

import com.lucianogiardino.mercadolivre.data.repository.ProductRepository
import com.lucianogiardino.mercadolivre.domain.model.ProductModel
import javax.inject.Inject
class GetProductDetailUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(productModel: ProductModel): ProductModel {

        val description = try {
            repository.getProductDescription(productModel.id)
        } catch (e: Exception) {
            ""
        }

        val pictures = try {
            productModel.pictures.ifEmpty {
                repository.getProductDetail(productModel.id).pictures
            }
        } catch (e: Exception) {
            listOf()
        }

        return productModel.copy(
            description = description,
            pictures = pictures
        )
    }
}