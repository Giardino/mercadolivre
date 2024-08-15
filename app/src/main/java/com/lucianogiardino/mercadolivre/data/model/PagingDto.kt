package com.lucianogiardino.mercadolivre.data.model

data class PagingDto(
    val limit: Int,
    val offset: Int,
    val primary_results: Int,
    val total: Int
)