package com.example.coco.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coco.dataModel.CurrentPrice
import com.example.coco.dataModel.CurrentPriceResult
import com.example.coco.dataStore.MyDataStore
import com.example.coco.repository.NetworkRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import timber.log.Timber

class SelectViewModel: ViewModel() {

    private val networkRepository = NetworkRepository()
    private lateinit var currentPriceResultList: ArrayList<CurrentPriceResult>

    private val _currentPriceResult = MutableLiveData<List<CurrentPriceResult>>()

    val currentPriceResult: LiveData<List<CurrentPriceResult>>
        get() = _currentPriceResult

    fun getCurrentCoinList() = viewModelScope.launch {
        val result = networkRepository.getCurrentCoinList()

        currentPriceResultList = ArrayList()
        for(coin in result.data){

            try{
                val gson = Gson()
                val gsonToGson = gson.toJson(result.data.get(coin.key))
                val gsonFromJson = gson.fromJson(gsonToGson, CurrentPrice::class.java)
                val currentPriceResult = CurrentPriceResult(coin.key, gsonFromJson)
                Timber.d(currentPriceResult.toString())
                currentPriceResultList.add(currentPriceResult)

            }catch (e: java.lang.Exception){
                Timber.d(e.toString())
            }
        }

        _currentPriceResult.value = currentPriceResultList
    }

    fun setUpFirstFlag() = viewModelScope.launch {
        MyDataStore().setupFirstData()
    }


}