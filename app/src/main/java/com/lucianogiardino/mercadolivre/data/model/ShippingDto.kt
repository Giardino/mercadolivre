package com.lucianogiardino.mercadolivre.data.model

import com.google.gson.annotations.SerializedName

data class ShippingDto(
    @SerializedName("free_shipping") val freeShipping: Boolean,
    @SerializedName("mode") val mode: String,
    @SerializedName("tags") val tags: List<String>,
    @SerializedName("logistic_type") val logisticType: String,
    @SerializedName("store_pick_up") val storePickUp: Boolean
)