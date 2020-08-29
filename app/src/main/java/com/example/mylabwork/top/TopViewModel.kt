package com.example.mylabwork.top

import androidx.lifecycle.viewModelScope
import com.example.mylabwork.base.BaseGifStorageViewModel
import com.example.mylabwork.base.DevLifeApiStatus
import com.example.mylabwork.network.DevLifeApi
import kotlinx.coroutines.launch

class TopViewModel: BaseGifStorageViewModel() {

    init {
        downloadData()
    }

    override fun downloadData() {
        viewModelScope.launch {
            try {
                _status.value = DevLifeApiStatus.LOADING
                val getTopPropertiesDeferred =
                    DevLifeApi.retrofitService.getTopPropertiesAsync(0)
                _status.value = DevLifeApiStatus.DONE
                val result = getTopPropertiesDeferred.await()
                _property.value = result.result[0]
                currentPage++
            } catch (e: Exception) {
                _status.value = DevLifeApiStatus.ERROR
                _property.value = null
            }
        }
    }
}