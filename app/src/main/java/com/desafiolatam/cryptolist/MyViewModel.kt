package com.desafiolatam.cryptolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.desafiolatam.cryptolist.model.Repository
import kotlinx.coroutines.launch

class MyViewModel: ViewModel() {

    private val repository = Repository()

    init {
        viewModelScope.launch { repository.retrieveAssets() }
    }
    fun getAssets() = repository.getAssets()

    fun retrieveAssetDetail(id: String) = viewModelScope.launch {
        repository.retrieveAssetDetail(id)
    }
    fun getAsset(id: String) = repository.getAsset(id)
}
