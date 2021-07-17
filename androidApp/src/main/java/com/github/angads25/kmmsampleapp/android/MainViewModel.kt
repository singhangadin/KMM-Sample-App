package com.github.angads25.kmmsampleapp.android

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.angads25.kmmsampleapp.UseCaseProvider
import com.github.angads25.kmmsampleapp.repository.NetworkUseCase
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    val liveData = MutableLiveData<String?>()
    val networkUseCase: NetworkUseCase = UseCaseProvider.networkUseCase

    fun dummyNetworkCall(query: String, page: String) {
        viewModelScope.launch {
            val data = networkUseCase.getNetworkData(query, page)
            if (data.isSuccess) {
                liveData.postValue(data.data.toString())
            } else {
                liveData.postValue(data.exception?.message)
                data.exception?.printStackTrace()
            }
        }
    }
}