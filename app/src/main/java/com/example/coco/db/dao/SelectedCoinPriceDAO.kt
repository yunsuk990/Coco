package com.example.coco.db.dao

import androidx.room.*
import com.example.coco.db.entity.SelectedCoinPriceEntity

@Dao
interface SelectedCoinPriceDAO {

    //getAllData
    @Query("SELECT * FROM selected_coin_price_table")
    fun getAllData(): List<SelectedCoinPriceEntity>

    //insert
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(selectedCoinPriceEntity: SelectedCoinPriceEntity)

    //하나의 코인에 대한 정보 가져오기
    @Query("SELECT * FROM selected_coin_price_table WHERE coinName =:coinName" )
    fun getOneCoinData(coinName: String): List<SelectedCoinPriceEntity>




}