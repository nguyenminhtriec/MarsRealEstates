package com.theways.marsphotoscompose.ui

import com.theways.marsphotoscompose.data.MarsPhoto
import java.text.NumberFormat

fun MarsPhoto.toItemUiState(selected: Boolean): ItemUiState {
    return ItemUiState(
        id = id,
        imgSrc = imgSrc,
        type = type,
        price = price.toString(),
        selected = selected,
    )
}

fun ItemUiState.toMarsPhoto(): MarsPhoto {
    return MarsPhoto(
        id = id,
        imgSrc = imgSrc,
        type = type,
        price = price.toIntOrNull() ?: 0,
    )
}

fun Int.toCurrency(): String {
    return NumberFormat.getCurrencyInstance().format(this)
}