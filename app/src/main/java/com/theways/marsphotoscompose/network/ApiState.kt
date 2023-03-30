package com.theways.marsphotoscompose.network

import androidx.annotation.DrawableRes
import com.theways.marsphotoscompose.R

enum class ApiState(@DrawableRes val icon: Int) {
    Loading(R.drawable.loading_img),
    Success(0),
    Error(R.drawable.ic_connection_error)
}