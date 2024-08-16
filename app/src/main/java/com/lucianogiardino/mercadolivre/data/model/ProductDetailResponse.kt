package com.lucianogiardino.mercadolivre.data.model

import PictureDto
import com.google.gson.annotations.SerializedName

data class ProductDetailResponse(
    @SerializedName("id") val id: String,
    @SerializedName("site_id") val siteId: String,
    @SerializedName("title") val title: String,
    @SerializedName("price") val price: Double,
    @SerializedName("base_price") val basePrice: Double,
    @SerializedName("original_price") val originalPrice: Double?,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("pictures") val pictures: List<PictureDto>,
)
