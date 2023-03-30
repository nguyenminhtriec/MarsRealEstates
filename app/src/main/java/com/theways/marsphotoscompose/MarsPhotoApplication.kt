package com.theways.marsphotoscompose

import android.app.Application
import com.theways.marsphotoscompose.data.Container
import com.theways.marsphotoscompose.data.ImplContainer

class MarsPhotoApplication : Application() {

    lateinit var appContainer: Container

    override fun onCreate() {
        super.onCreate()
        appContainer = ImplContainer()
    }
}