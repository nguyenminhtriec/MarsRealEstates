package com.theways.marsphotoscompose.network

import com.theways.marsphotoscompose.data.MarsPhoto
import retrofit2.http.GET

interface MarsApiService {

    @GET("realestate")
    suspend fun getPhotos(): List<MarsPhoto>
}