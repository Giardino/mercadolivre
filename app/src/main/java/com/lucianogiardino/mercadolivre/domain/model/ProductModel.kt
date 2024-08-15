package com.lucianogiardino.mercadolivre.domain.model

data class ProductModel(
    val id: String,
    val name: String,
    val price: Double,
    val thumbnail: String
)