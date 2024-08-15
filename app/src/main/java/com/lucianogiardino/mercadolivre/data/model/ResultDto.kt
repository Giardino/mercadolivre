package com.lucianogiardino.mercadolivre.data.model

import com.google.gson.annotations.SerializedName

data class ResultDto(
    @SerializedName("id") val id: String,
    @SerializedName("site_id") val siteId: String,
    @SerializedName("title") val title: String,
    @SerializedName("price") val price: Double,
    @SerializedName("currency_id") val currencyId: String,
    @SerializedName("available_quantity") val availableQuantity: Int,
    @SerializedName("buying_mode") val buyingMode: String,
    @SerializedName("listing_type_id") val listingTypeId: String,
    @SerializedName("stop_time") val stopTime: String,
    @SerializedName("condition") val condition: String,
    @SerializedName("permalink") val permalink: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("accepts_mercadopago") val acceptsMercadopago: Boolean,
    @SerializedName("installments") val installments: InstallmentsDto?,
    @SerializedName("shipping") val shipping: ShippingDto,
    @SerializedName("attributes") val attributes: List<AttributeDto>,
    @SerializedName("original_price") val originalPrice: Any?,
    @SerializedName("category_id") val categoryId: String,
    @SerializedName("official_store_id") val officialStoreId: Int?,
    @SerializedName("catalog_product_id") val catalogProductId: String,
    @SerializedName("catalog_listing") val catalogListing: Boolean
)