package com.nmt.nguyen.nasamarsphotos.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nmt.nguyen.nasamarsphotos.network.MarsApiFilter
import com.nmt.nguyen.nasamarsphotos.network.MarsPhoto
import com.nmt.nguyen.nasamarsphotos.network.MarsRetrofit
import kotlinx.coroutines.launch

class PhotoViewModel : ViewModel() {

    private val _status= MutableLiveData<MarsApiStatus>()
    val status: LiveData<MarsApiStatus> get() = _status

    private val _photos = MutableLiveData<List<MarsPhoto>>()
    val photos: LiveData<List<MarsPhoto>> get() = _photos

    private val _selectedPhoto = MutableLiveData<MarsPhoto>()
    val selectedPhoto: LiveData<MarsPhoto> get() = _selectedPhoto

    init {
        getMarsPhotos(MarsApiFilter.ALL)
    }

    private fun getMarsPhotos(marsApiFilter: MarsApiFilter) {
        viewModelScope.launch {
            _status.value = MarsApiStatus.LOADING
            try {
                val listPhotos: List<MarsPhoto> = MarsRetrofit.retrofitService.getPhotos(marsApiFilter.filter)
                _photos.value = listPhotos
                _status.value = MarsApiStatus.SUCCESS
            } catch (e: Exception) {
                _status.value = MarsApiStatus.FAILURE
            }
        }
    }

    fun updateFilter(marsApiFilter: MarsApiFilter) {
        getMarsPhotos(marsApiFilter)
    }

    fun getSelecttedPhoto(marsPhoto: MarsPhoto) {
        _selectedPhoto.value = marsPhoto
    }
}

enum class MarsApiStatus { LOADING, SUCCESS, FAILURE }