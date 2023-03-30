package com.theways.marsphotoscompose.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.theways.marsphotoscompose.network.MarsApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

interface Container {
    val implRepository: Repository
}

class ImplContainer : Container {

    private val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/"

    private val retrofit: Retrofit = Retrofit.Builder()
        //.addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
    override val implRepository by lazy {
        ImplRepository(retrofitService)
    }
}



