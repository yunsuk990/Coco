package com.example.coco.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.coco.dataModel.UpDownDataSet
import com.example.coco.db.entity.InterestCoinEntity
import com.example.coco.repository.DBRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel: ViewModel() {

    private val dbRepository = DBRepository()
    lateinit var selectedCoinList: LiveData<List<InterestCoinEntity>>

    private val _arr15min = MutableLiveData<List<UpDownDataSet>>()
    val arr15min: LiveData<List<UpDownDataSet>>
        get() = _arr15min

    private val _arr30min = MutableLiveData<List<UpDownDataSet>>()
    val arr30min: LiveData<List<UpDownDataSet>>
        get() = _arr30min
    private val _arr45min = MutableLiveData<List<UpDownDataSet>>()
    val arr45min: LiveData<List<UpDownDataSet>>
        get() = _arr45min


    // CoinListFragment
    fun getAllInterestCoinData() = viewModelScope.launch {
        val coinList = dbRepository.getAllInterstCoinData().asLiveData()
        selectedCoinList = coinList
    }

    fun updateInterestCoinData(interestCoinEntity: InterestCoinEntity) = viewModelScope.launch(Dispatchers.IO) {
        if(interestCoinEntity.selected){
            interestCoinEntity.selected = false
        }else{
            interestCoinEntity.selected = true
        }
        dbRepository.updateInterestCoinData(interestCoinEntity)

    }


    // PriceChangeFragment

    fun getAllSelectedCoinData() = viewModelScope.launch(Dispatchers.IO) {

        val arr15min = ArrayList<UpDownDataSet>()
        val arr30min = ArrayList<UpDownDataSet>()
        val arr45min = ArrayList<UpDownDataSet>()

        val selectedCoinList = dbRepository.getAllInterestSelectedCoinData()
        for(data in selectedCoinList){
            val coinName = data.coin_name
            val oneCoinData = dbRepository.getOneSelectedCoinData(coinName).reversed()

            val size = oneCoinData.size
            if(size > 1){
                val changePrice = oneCoinData[0].price.toDouble() - oneCoinData[1].price.toDouble()
                val upDownDataset = UpDownDataSet(
                    coinName,
                    changePrice.toString()
                )
                arr15min.add(upDownDataset)
            }
            if(size > 2){
                val changePrice = oneCoinData[0].price.toDouble() - oneCoinData[2].price.toDouble()
                val upDownDataset = UpDownDataSet(
                    coinName,
                    changePrice.toString()
                )
                arr30min.add(upDownDataset)
            }
            if(size > 3){
                val changePrice = oneCoinData[0].price.toDouble() - oneCoinData[3].price.toDouble()
                val upDownDataset = UpDownDataSet(
                    coinName,
                    changePrice.toString()
                )
                arr45min.add(upDownDataset)
            }
        }

        withContext(Dispatchers.Main){
            _arr15min.value = arr15min
            _arr30min.value = arr30min
            _arr45min.value = arr45min
        }

    }


}