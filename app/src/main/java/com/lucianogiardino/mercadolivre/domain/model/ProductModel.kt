package com.lucianogiardino.mercadolivre.domain.model

data class ProductModel(
    val id: String,
    val name: String,
    val originalPrice: Double,
    val price: Double,
    val thumbnail: String,
    val description: String,
    val pictures: List<String>
)