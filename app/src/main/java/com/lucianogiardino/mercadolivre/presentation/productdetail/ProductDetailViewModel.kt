package com.lucianogiardino.mercadolivre.presentation.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucianogiardino.mercadolivre.domain.model.ProductModel
import com.lucianogiardino.mercadolivre.domain.usecase.GetProductDetailUseCase
import com.lucianogiardino.mercadolivre.domain.usecase.SearchProductsUseCase
import com.lucianogiardino.mercadolivre.presentation.StateUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductDetailUseCase: GetProductDetailUseCase
) : ViewModel() {

    private val _productsState = MutableLiveData<StateUI<ProductModel>>()
    val productsState: LiveData<StateUI<ProductModel>> = _productsState

    fun getProductDetail(productModel: ProductModel) {
        _productsState.value = StateUI.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = getProductDetailUseCase(productModel)
                _productsState.postValue(StateUI.Success(result))
            } catch (e: Exception) {
                _productsState.postValue(StateUI.Error(e))
            }
        }
    }

}