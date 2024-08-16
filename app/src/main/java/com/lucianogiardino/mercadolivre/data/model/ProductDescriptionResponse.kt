package com.lucianogiardino.mercadolivre.data.model

import com.google.gson.annotations.SerializedName

data class ProductDescriptionResponse(
    @SerializedName("text") val text: String,
    @SerializedName("plain_text") val plainText: String,
    @SerializedName("last_updated") val lastUpdated: String,
    @SerializedName("date_created") val dateCreated: String,
)