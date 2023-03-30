package com.theways.marsphotoscompose.ui

import com.theways.marsphotoscompose.data.MarsPhoto

data class ItemUiState(
    val id: String = "",
    val imgSrc: String = "",
    val type: String = "",
    val price: String = "",
    val selected: Boolean = false,
)

data class HomeUiState(
    val photos: List<MarsPhoto> = listOf()
)

data class MappedHomeUIState(
    val mappedPhotos: Map<String, List<MarsPhoto>> = mapOf()
)
