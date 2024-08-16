import com.lucianogiardino.mercadolivre.data.repository.ProductRepository
import com.lucianogiardino.mercadolivre.domain.model.ProductModel
import com.lucianogiardino.mercadolivre.domain.usecase.SearchProductsUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import kotlin.test.assertEquals

class SearchProductsUseCaseTest {

    private lateinit var repository: ProductRepository
    private lateinit var searchProductsUseCase: SearchProductsUseCase

    @Before
    fun setUp() {
        repository = mock(ProductRepository::class.java)
        searchProductsUseCase = SearchProductsUseCase(repository)
    }

    @Test
    fun `return list of products`(): Unit = runBlocking {
        val query = "produto"
        val expectedProducts = listOf(
            ProductModel(
                id = "1",
                name = "Produto 1",
                price = 100.0,
                originalPrice = 120.0,
                description = "descricao do Produto 1",
                pictures = listOf("https://algumacoisa.com"),
                thumbnail = "https://algumacoisa.com"
            ),
            ProductModel(
                id = "2",
                name = "Produto 2",
                price = 200.0,
                originalPrice = 220.0,
                description = "descricao do Produto 2",
                pictures = listOf("https://algumacoisa.com"),
                thumbnail = "https://algumacoisa.com"
            )
        )

        `when`(repository.findProducts(query)).thenReturn(expectedProducts)

        val result = searchProductsUseCase(query)

        assertEquals(expectedProducts, result)
        verify(repository).findProducts(query)
    }

    @Test
    fun `return empty list no products found`(): Unit = runBlocking {
        val query = "produto inexistente"
        val expectedProducts = emptyList<ProductModel>()

        `when`(repository.findProducts(query)).thenReturn(expectedProducts)

        val result = searchProductsUseCase(query)

        assertEquals(expectedProducts, result)
        verify(repository).findProducts(query)
    }
}
