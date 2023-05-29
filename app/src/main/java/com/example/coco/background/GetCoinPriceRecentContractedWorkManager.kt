package com.example.coco.background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.coco.dataModel.RecentPriceData
import com.example.coco.dataModel.RecentPriceDataList
import com.example.coco.db.entity.SelectedCoinPriceEntity
import com.example.coco.repository.DBRepository
import com.example.coco.repository.NetworkRepository
import timber.log.Timber
import java.sql.Timestamp
import java.util.*

//최근 거래된 코인 가격 내역을 가져오는 WorkManager
//1.관심 있는 코인 리스트 가져오기
//2.관심 있는 코인 각각의 가격 변동 정보 가져오기
//3.관심 있는 코인 각각의 가격 변동 정보를 db에 저장

class GetCoinPriceRecentContractedWorkManager(val context: Context, workerParameters: WorkerParameters)
    : CoroutineWorker(context, workerParameters){

    private val dbRepository = DBRepository()
    private val networkRepository = NetworkRepository()
    val timestamp = Calendar.getInstance().time

    override suspend fun doWork(): Result {
        Timber.d("doWork")
        getAllInterestSelectedCoinData()
        return Result.success()
    }

    suspend fun getAllInterestSelectedCoinData(){

        val selectedCoinList = dbRepository.getAllInterestSelectedCoinData()
        for(item in selectedCoinList){
            Timber.d(item.toString())

           val recentPriceList =  networkRepository.getInterestCoinPriceData(item.coin_name)

            saveSelectedCoinPrice(
                item.coin_name,
                recentPriceList,
                timestamp
            )
        }
    }

    fun saveSelectedCoinPrice(
        coinName: String,
        recentCoinPriceList: RecentPriceDataList,
        timestamp: Date
    ){
        val selectedCoinPriceEntity = SelectedCoinPriceEntity(
            0,
            coinName,
            recentCoinPriceList.data[0].transaction_date,
            recentCoinPriceList.data[0].type,
            recentCoinPriceList.data[0].units_traded,
            recentCoinPriceList.data[0].price,
            recentCoinPriceList.data[0].total,
            timestamp
        )

        dbRepository.insertCoinPriceData(selectedCoinPriceEntity)
    }

}