package com.lucianogiardino.mercadolivre.data.model

import PictureDto
import com.google.gson.annotations.SerializedName

data class ProductDetailResponse(
    @SerializedName("id") val id: String,
    @SerializedName("site_id") val siteId: String,
    @SerializedName("title") val title: String,
    @SerializedName("seller_id") val sellerId: Long,
    @SerializedName("category_id") val categoryId: String,
    @SerializedName("official_store_id") val officialStoreId: Int?,
    @SerializedName("price") val price: Double,
    @SerializedName("base_price") val basePrice: Double,
    @SerializedName("original_price") val originalPrice: Double?,
    @SerializedName("currency_id") val currencyId: String,
    @SerializedName("initial_quantity") val initialQuantity: Int,
    @SerializedName("buying_mode") val buyingMode: String,
    @SerializedName("listing_type_id") val listingTypeId: String,
    @SerializedName("condition") val condition: String,
    @SerializedName("permalink") val permalink: String,
    @SerializedName("thumbnail_id") val thumbnailId: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("pictures") val pictures: List<PictureDto>,
    @SerializedName("video_id") val videoId: String?,
    @SerializedName("descriptions") val descriptions: List<String>,
    @SerializedName("accepts_mercadopago") val acceptsMercadopago: Boolean,
    @SerializedName("non_mercado_pago_payment_methods") val nonMercadoPagoPaymentMethods: List<Any>,

)
