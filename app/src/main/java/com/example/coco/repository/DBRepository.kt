package com.example.coco.repository

import com.example.coco.App
import com.example.coco.db.CoinPriceDatabase
import com.example.coco.db.entity.InterestCoinEntity

class DBRepository {

    val context = App.context()
    val db = CoinPriceDatabase.getDatabase(context)

    //InterestCoin
    fun getAllInterstCoinData() = db.interestCoinDAO().getAllData()

    fun insertInterstCoinData(interestCoinEntity: InterestCoinEntity) = db.interestCoinDAO().insert(interestCoinEntity)

    fun updateInterestCoinData(interestCoinEntity: InterestCoinEntity) = db.interestCoinDAO().update(interestCoinEntity)

    fun getAllInterestSelectedCoinData() = db.interestCoinDAO().getSelectedData()

}