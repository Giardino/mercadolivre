package com.lucianogiardino.mercadolivre

import PictureDto
import com.lucianogiardino.mercadolivre.data.model.FindProductsResponse
import com.lucianogiardino.mercadolivre.data.model.ProductDescriptionResponse
import com.lucianogiardino.mercadolivre.data.model.ProductDetailResponse
import com.lucianogiardino.mercadolivre.data.model.ResultDto
import com.lucianogiardino.mercadolivre.data.remote.ApiService
import com.lucianogiardino.mercadolivre.data.repository.ProductRepositoryImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import retrofit2.Response
import kotlin.test.assertFailsWith

class ProductRepositoryImplTest {

    private lateinit var apiService: ApiService
    private lateinit var repository: ProductRepositoryImpl

    @Before
    fun setUp() {
        apiService = mock(ApiService::class.java)
        repository = ProductRepositoryImpl(apiService)
    }

    @Test
    fun `findProducts return list of ProductModel`() = runBlocking {
        val query = "test"
        val resultDto = ResultDto(
            id = "1",
            title = "produto",
            price = 59.0,
            originalPrice = 100.0,
            thumbnail = "http://algumacoisa.com.br",
        )
        val findProductsResponse = FindProductsResponse(results = listOf(resultDto))
        val response = Response.success(findProductsResponse)

        `when`(apiService.searchProduct(query)).thenReturn(response)

        val products = repository.findProducts(query)

        assertEquals(1, products.size)
        assertEquals("1", products[0].id)
        assertEquals("produto", products[0].name)
        assertEquals(59.0, products[0].price)
        assertEquals(100.0, products[0].originalPrice)
        assertEquals("https://algumacoisa.com.br", products[0].thumbnail)
    }

    @Test
    fun `findProducts throw exception when body is null`(): Unit = runBlocking {
        val query = "test"
        val response = Response.success<FindProductsResponse>(null)

        `when`(apiService.searchProduct(query)).thenReturn(response)

        assertFailsWith<Exception> {
            repository.findProducts(query)
        }
    }

    @Test
    fun `findProducts throw exception when is unsuccessful`(): Unit = runBlocking {
        val query = "test"
        val response = Response.error<FindProductsResponse>(404, mock(ResponseBody::class.java))

        `when`(apiService.searchProduct(query)).thenReturn(response)

        assertFailsWith<Exception> {
            repository.findProducts(query)
        }
    }

    @Test
    fun `getProductDetail  return ProductModel when is successful`() = runBlocking {
        val productId = "1"
        val productDetailResponse = ProductDetailResponse(
            id = "1",
            title = "Produto 1",
            price = 100.0,
            originalPrice = 150.0,
            basePrice = 0.0,
            siteId = "",
            thumbnail = "http://algumacoisa.com.br",
            pictures = listOf(PictureDto("http://algumacoisa.com.br"))
        )
        val response = Response.success(productDetailResponse)

        `when`(apiService.getProductDetail(productId)).thenReturn(response)

        val product = repository.getProductDetail(productId)

        assertEquals("1", product.id)
        assertEquals("Produto 1", product.name)
        assertEquals(100.0, product.price)
        assertEquals(150.0, product.originalPrice)
        assertEquals("https://algumacoisa.com.br", product.thumbnail)
        assertEquals(1, product.pictures.size)
        assertEquals("http://algumacoisa.com.br", product.pictures[0])
    }

    @Test
    fun `getProductDetail throw exception when is unsuccessful`(): Unit = runBlocking {
        val productId = "1"
        val response = Response.error<ProductDetailResponse>(404, mock(ResponseBody::class.java))

        `when`(apiService.getProductDetail(productId)).thenReturn(response)

        assertFailsWith<Exception> {
            repository.getProductDetail(productId)
        }
    }

    @Test
    fun `getProductDescription return description when is successful`() = runBlocking {
        // Arrange
        val productId = "1"
        val productDescriptionResponse = ProductDescriptionResponse(
            plainText = "alguma coisa blabla",
            text = "",
            dateCreated = "",
            lastUpdated = ""
        )
        val response = Response.success(productDescriptionResponse)

        `when`(apiService.getProductDescription(productId)).thenReturn(response)

        val description = repository.getProductDescription(productId)

        assertEquals("alguma coisa blabla", description)
    }

    @Test
    fun `getProductDescription throw exception when is unsuccessful`(): Unit = runBlocking {
        val productId = "1"
        val response = Response.error<ProductDescriptionResponse>(404, mock(ResponseBody::class.java))

        `when`(apiService.getProductDescription(productId)).thenReturn(response)

        assertFailsWith<Exception> {
            repository.getProductDescription(productId)
        }
    }
}
