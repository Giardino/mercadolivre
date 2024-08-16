package com.lucianogiardino.mercadolivre.data.model

import com.google.gson.annotations.SerializedName

data class ResultDto(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("price") val price: Double,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("original_price") val originalPrice: Double,

)