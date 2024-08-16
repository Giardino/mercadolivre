package com.lucianogiardino.mercadolivre

import com.lucianogiardino.mercadolivre.data.repository.ProductRepository
import com.lucianogiardino.mercadolivre.domain.model.ProductModel
import com.lucianogiardino.mercadolivre.domain.usecase.GetProductDetailUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import kotlin.test.assertEquals

class GetProductDetailUseCaseTest {

    private lateinit var repository: ProductRepository
    private lateinit var getProductDetailUseCase: GetProductDetailUseCase

    @Before
    fun setUp() {
        repository = mock(ProductRepository::class.java)
        getProductDetailUseCase = GetProductDetailUseCase(repository)
    }

    @Test
    fun `return product with description and images when available`() = runBlocking {
        val productId = "123"
        val product = ProductModel(
            id = productId,
            name = "Produto 1",
            price = 100.0,
            originalPrice = 120.0,
            description = "",
            pictures = emptyList(),
            thumbnail = "https://algumacoisa.com.br"
        )

        val descricao = "descricao"
        val imagens = listOf("https://algumacoisa2", "https://algunacoisa3")

        `when`(repository.getProductDescription(productId)).thenReturn(descricao)
        `when`(repository.getProductDetail(productId)).thenReturn(product.copy(pictures = imagens))

        val resultado = getProductDetailUseCase(product)

        assertEquals(descricao, resultado.description)
        assertEquals(imagens, resultado.pictures)
    }

    @Test
    fun `return product with empty description if repository throws exception`() = runBlocking {
        val productid = "123"
        val product = ProductModel(
            id = productid,
            name = "Produto 1",
            price = 100.0,
            originalPrice = 120.0,
            description = "",
            pictures = emptyList(),
            thumbnail = "https://exemplo.com/imagem.jpg"
        )

        `when`(repository.getProductDescription(productid)).thenThrow(RuntimeException::class.java)
        `when`(repository.getProductDetail(productid)).thenReturn(product.copy(pictures = emptyList()))

        val resultado = getProductDetailUseCase(product)

        assertEquals("", resultado.description)
        assertEquals(emptyList<String>(), resultado.pictures)
    }
}
