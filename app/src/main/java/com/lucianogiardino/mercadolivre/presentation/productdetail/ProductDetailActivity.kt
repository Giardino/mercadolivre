package com.lucianogiardino.mercadolivre.presentation.productdetail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.gson.Gson
import com.lucianogiardino.mercadolivre.domain.model.ProductModel
import com.lucianogiardino.mercadolivre.presentation.StateUI
import com.lucianogiardino.mercadolivre.presentation.searchproducts.CenteredCircularProgressIndicator
import com.lucianogiardino.mercadolivre.presentation.searchproducts.ProductsListContent
import com.lucianogiardino.mercadolivre.presentation.searchproducts.SearchProductsScreen
import com.lucianogiardino.mercadolivre.presentation.searchproducts.SearchProductsViewModel
import com.lucianogiardino.mercadolivre.presentation.theme.MercadolivreTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailActivity : ComponentActivity() {

    private val viewModel: ProductDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val productJson = intent.getStringExtra("product")
        val product = Gson().fromJson(productJson, ProductModel::class.java)
        setContent{
            ProductDetailScreen(viewModel,product)
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(viewModel: ProductDetailViewModel,productModel: ProductModel) {
    val context = LocalContext.current as? ComponentActivity
    var productModel by remember { mutableStateOf(productModel) }
    val productsState by viewModel.productsState.observeAsState(StateUI.Waiting)

    LaunchedEffect(productModel) {
        viewModel.getProductDetail(productModel)
    }

    MercadolivreTheme {
        Scaffold(
            topBar = {
                SimpleTopAppBar(onBackClick = {  context?.finish() })
            },
            content = {
                    paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                ) {
                    when (productsState) {
                        is StateUI.Waiting -> {

                        }
                        is StateUI.Loading -> {
                            CenteredCircularProgressIndicator()
                        }
                        is StateUI.Success -> {
                            val product = (productsState as StateUI.Success<ProductModel>).data
                            ProductDetailContent(product)                        }
                        is StateUI.Error -> {
                            Text("Ops! tivemos algum problema, tente novamente mais tarde")
                        }
                    }
                }
            }
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun SimpleTopAppBar(onBackClick: () -> Unit) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun ProductDetailContent(productModel: ProductModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        val scrollState = rememberScrollState()

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(16.dp)
                .verticalScroll(scrollState)
        ) {

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(productModel.pictures.size) { index ->

                    AsyncImage(
                        model = productModel.pictures[index],
                        contentDescription = null,
                        modifier = Modifier
                            .height(300.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.FillHeight
                    )

                }
            }

            Text(
                text = productModel.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
            )

            if (productModel.originalPrice != 0.0){
                Text(
                    text = "R$ ${productModel.originalPrice}",
                    style = MaterialTheme.typography.titleSmall.copy(
                        textDecoration = TextDecoration.LineThrough
                    ),
                    color = Color.Black,
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            Text(
                text = "R$ ${productModel.price}",
                style = MaterialTheme.typography.titleSmall,
                color = Color.Black,
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = productModel.description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
            )
        }

    }

}