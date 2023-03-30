package com.theways.marsphotoscompose.ui

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.theways.marsphotoscompose.MarsPhotoApplication
import com.theways.marsphotoscompose.data.MarsPhoto
import com.theways.marsphotoscompose.data.Repository
import com.theways.marsphotoscompose.network.ApiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MarsPhotoViewModel(private val marsRepository: Repository) : ViewModel() {

    var apiState by mutableStateOf(ApiState.Loading)
        private set

    var contentFilter by mutableStateOf(ContentFilter.ALL)
        private set

    var itemUiState by mutableStateOf(ItemUiState())
        private set

    var homeUiState by mutableStateOf(HomeUiState())
        private set

    init { updateHomeUiState() }

    fun updateHomeUiState() {
        viewModelScope.launch {
            apiState = ApiState.Loading
            try {
                val updated = marsRepository.getPhotos()
                homeUiState = homeUiState.copy(photos = updated)
                apiState = ApiState.Success

            } catch (e: IOException) {
                Log.e("__NWT__", e.message ?: "____")
                apiState = ApiState.Error
            }
            catch (e: HttpException) {
                Log.e("__NWT__", e.message())
                apiState = ApiState.Error
            }
        }
    }

    fun updateItemUiState(newItemUiState: ItemUiState) {
        itemUiState = newItemUiState.copy(selected = true)
    }

    fun updateContentFilter(newFilter: ContentFilter) {
        contentFilter = newFilter
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MarsPhotoViewModel(marsphotoApplication().appContainer.implRepository)
            }
        }
    }
}

private fun CreationExtras.marsphotoApplication(): MarsPhotoApplication {
    return this[APPLICATION_KEY] as MarsPhotoApplication
}