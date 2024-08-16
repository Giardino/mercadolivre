package com.lucianogiardino.mercadolivre.data.model

import com.google.gson.annotations.SerializedName

data class FindProductsResponse(
    @SerializedName("results") val results: List<ResultDto>
)