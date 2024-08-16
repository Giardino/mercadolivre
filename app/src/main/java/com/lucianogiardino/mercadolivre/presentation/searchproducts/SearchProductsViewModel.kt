package com.lucianogiardino.mercadolivre.presentation.searchproducts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucianogiardino.mercadolivre.domain.model.ProductModel
import com.lucianogiardino.mercadolivre.domain.usecase.SearchProductsUseCase
import com.lucianogiardino.mercadolivre.presentation.StateUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SearchProductsViewModel @Inject constructor(
    private val searchProductsUseCase: SearchProductsUseCase
) : ViewModel() {

    private val _productsState = MutableLiveData<StateUI<List<ProductModel>>>()
    val productsState: LiveData<StateUI<List<ProductModel>>> = _productsState

    fun searchProduct(query: String) {
        _productsState.value = StateUI.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = searchProductsUseCase(query)
                _productsState.postValue(StateUI.Success(result))
            } catch (e: Exception) {
                _productsState.postValue(StateUI.Error(e))
            }
        }
    }

}