package com.theways.marsphotoscompose.data

import com.theways.marsphotoscompose.network.MarsApiService
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getPhotos(): List<MarsPhoto>
}

class ImplRepository(private val retrofitService: MarsApiService) : Repository {
    override suspend fun getPhotos(): List<MarsPhoto> = retrofitService.getPhotos()
}