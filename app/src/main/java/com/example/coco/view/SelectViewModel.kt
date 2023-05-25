package com.example.coco.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coco.dataModel.CurrentPrice
import com.example.coco.dataModel.CurrentPriceResult
import com.example.coco.dataStore.MyDataStore
import com.example.coco.db.entity.InterestCoinEntity
import com.example.coco.repository.DBRepository
import com.example.coco.repository.NetworkRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class SelectViewModel: ViewModel() {

    private val networkRepository = NetworkRepository()
    private val dbRepository = DBRepository()
    private lateinit var currentPriceResultList: ArrayList<CurrentPriceResult>


    private val _currentPriceResult = MutableLiveData<List<CurrentPriceResult>>()
    private val success_lock = MutableLiveData<String>()

    val currentPriceResult: LiveData<List<CurrentPriceResult>>
        get() = _currentPriceResult

    val save: LiveData<String>
        get() = success_lock


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

    //DB에 데이터 저장
    fun saveSelectedCoinList(selectedCoinList: ArrayList<String>) = viewModelScope.launch(Dispatchers.IO){
        //1. 전체 코인 데이터 가져오기
        for(coin in currentPriceResultList){
            Timber.d(coin.toString())

            //2. 내가 선택한 코인인지 아닌지 구분
            val selected = selectedCoinList.contains(coin.coinName)
            val interestCoinEntity = InterestCoinEntity(
                0,
                coin.coinName,
                coin.coinInfo.opening_price,
                coin.coinInfo.closing_price,
                coin.coinInfo.min_price,
                coin.coinInfo.max_price,
                coin.coinInfo.units_traded,
                coin.coinInfo.acc_trade_value,
                coin.coinInfo.prev_closing_price,
                coin.coinInfo.units_traded_24H,
                coin.coinInfo.acc_trade_value_24H,
                coin.coinInfo.fluctate_24H,
                coin.coinInfo.fluctate_rate_24H,
                selected
            )

            //3. 저장
            interestCoinEntity.let {
                dbRepository.insertInterstCoinData(it)
            }
        }

        withContext(Dispatchers.Main){
            success_lock.value = "done"
        }



    }


}