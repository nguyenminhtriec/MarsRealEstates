package com.theways.marsphotoscompose.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class MarsPhoto(
    val id: String,
    @SerialName(value = "img_src") val imgSrc: String,
    val type: String,
    val price: Int,
)
