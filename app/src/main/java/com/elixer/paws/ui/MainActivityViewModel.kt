package com.elixer.paws.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elixer.paws.interactors.GetDogs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(private val getDogs: GetDogs) : ViewModel() {

    private val _imageUrl = MutableLiveData("")
    val imageUrl: LiveData<String> = _imageUrl

    private val _status = MutableLiveData("")
     val status: LiveData<String> = _status


    private val _statusResourceId = MutableLiveData<Int>(null)
    val statusResourceId: MutableLiveData<Int> = _statusResourceId

    val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    init {
        loadImage("hound")
    }

    internal fun loadImage(breed: String) {
        _loading.value = true
        viewModelScope.launch {
            getDogs.execute(breed).onEach { dataState ->

                when(dataState){
                    is ResultWrapper.Success<String> ->{
                        _imageUrl.value = dataState.value
//                        _status.value = dataState.status
                        _statusResourceId.value = dataState.statusResourceId
                        _loading.value = false
                    }

                    is ResultWrapper.GenericError ->{
                        _imageUrl.value = ""
//                        _status.value = dataState.status
                        _statusResourceId.value = dataState.statusResourceId
                        _loading.value = false
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}













