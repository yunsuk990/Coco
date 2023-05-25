package com.example.coco.db.dao

import androidx.room.*
import com.example.coco.db.entity.InterestCoinEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InterestCoinDAO {

    //getAllData
    @Query("SELECT * FROM interest_coin_table")
    fun getAllData(): Flow<List<InterestCoinEntity>>

    //Insert
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(insertCoinEntity: InterestCoinEntity)

    //update
    @Update
    fun update(interestCoinEntity: InterestCoinEntity)

    //getSelectedCoinList (관심있는 코인 데이터를 가져오기)
    @Query("SELECT * FROM interest_coin_table WHERE selected = :selected")
    fun getSelectedData(selected: Boolean = true): List<InterestCoinEntity>

}