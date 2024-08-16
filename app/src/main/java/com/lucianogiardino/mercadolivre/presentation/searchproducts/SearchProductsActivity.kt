package com.lucianogiardino.mercadolivre.presentation.searchproducts

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lucianogiardino.mercadolivre.presentation.theme.MercadolivreTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.ViewModel
import coil.compose.AsyncImage
import com.google.gson.Gson
import com.lucianogiardino.mercadolivre.domain.model.ProductModel
import com.lucianogiardino.mercadolivre.presentation.StateUI
import com.lucianogiardino.mercadolivre.presentation.productdetail.ProductDetailActivity


@AndroidEntryPoint
class SearchProductsActivity : ComponentActivity() {

    private val viewModel: SearchProductsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SearchProductsScreen(viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchProductsScreen(viewModel: SearchProductsViewModel) {
    val productsState by viewModel.productsState.observeAsState(StateUI.Waiting)
    var query by remember { mutableStateOf("") }
    val context = LocalContext.current
    var searchTriggered by rememberSaveable { mutableStateOf(false) }  // Estado para verificar se a busca foi acionada

    LaunchedEffect(query, searchTriggered) {
        if (query.isNotEmpty() && searchTriggered) {
            viewModel.searchProduct(query)
            searchTriggered = false  // Reseta o estado após a busca
        }
    }

    MercadolivreTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    ,
                verticalArrangement = Arrangement.Center,

            ){
                SearchBar(
                    query = query,
                    onQueryChange = { query = it },
                    onSearch = {
                        searchTriggered = true
                        hideKeyboard(context)
                    },
                    active = false,
                    onActiveChange = {},
                    placeholder = {
                        Text("Buscar...")
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),) {}
            }

            when (productsState) {
                is StateUI.Waiting -> {

                }
                is StateUI.Loading -> {
                    CenteredCircularProgressIndicator()
                }
                is StateUI.Success -> {
                    ProductsListContent(products = (productsState as StateUI.Success<List<ProductModel>>).data, context)
                }
                is StateUI.Error -> {
                    Text("Ops! tivemos algum problema, tente novamente mais tarde")
                }
            }

        }

    }
}

@Composable
fun ProductsListContent(products: List<ProductModel>, context: Context){

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(16.dp)
        ) {
            items(products.size) { index ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(8.dp)
                        .clickable {
                            val gson = Gson()
                            val productJson = gson.toJson(products[index])
                            val intent = Intent(context, ProductDetailActivity::class.java).apply {
                                putExtra("product", productJson)
                            }
                            context.startActivity(intent)
                        },

                ) {
                    AsyncImage(
                        model = products[index].thumbnail,
                        contentDescription = null,
                        modifier = Modifier
                            .height(120.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = products[index].name,
                        style = MaterialTheme.typography.labelMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    if (products[index].originalPrice != 0.0){
                        Text(
                            text = "R$ ${products[index].originalPrice}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                textDecoration = TextDecoration.LineThrough // Aplica o efeito riscado
                            ),
                            color = Color.Black,
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }

                    Text(
                        text = "R$ ${products[index].price}",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.Black,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                }
            }
        }
    }

}

@Composable
fun CenteredCircularProgressIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
fun hideKeyboard(context: Context) {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
}

