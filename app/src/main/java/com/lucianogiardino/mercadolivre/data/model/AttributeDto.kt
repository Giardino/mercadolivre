package com.lucianogiardino.mercadolivre.data.model

import com.google.gson.annotations.SerializedName

data class AttributeDto(
    @SerializedName("name") val name: String,
    @SerializedName("value_id") val valueId: String?,
    @SerializedName("value_name") val valueName: String,
    @SerializedName("value_struct") val valueStruct: Any?,
    @SerializedName("attribute_group_id") val attributeGroupId: String,
    @SerializedName("attribute_group_name") val attributeGroupName: String,
    @SerializedName("source") val source: String,
    @SerializedName("id") val id: String
)