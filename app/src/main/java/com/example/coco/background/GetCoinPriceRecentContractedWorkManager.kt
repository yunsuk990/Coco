package com.example.coco.background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.coco.repository.DBRepository
import timber.log.Timber

//최근 거래된 코인 가격 내역을 가져오는 WorkManager
//1.관심 있는 코인 리스트 가져오기
//2.관심 있는 코인 각각의 가격 변동 정보 가져오기
//3.관심 있는 코인 각각의 가격 변동 정보를 db에 저장

class GetCoinPriceRecentContractedWorkManager(val context: Context, workerParameters: WorkerParameters)
    : CoroutineWorker(context, workerParameters){

    private val dbRepository = DBRepository()

    override suspend fun doWork(): Result {
        Timber.d("doWork")
        return Result.success()
    }

    suspend fun getAllInterestSelectedCoinData(){

        val selectedCoinList = dbRepository.getAllInterestSelectedCoinData()
        for(item in selectedCoinList){

        }

    }

}