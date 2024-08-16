package com.lucianogiardino.mercadolivre.data.model

import com.google.gson.annotations.SerializedName

data class FindProductsResponse(
    @SerializedName("site_id") val siteId: String,
    @SerializedName("query") val query: String,
    @SerializedName("paging") val paging: PagingDto,
    @SerializedName("results") val results: List<ResultDto>
)