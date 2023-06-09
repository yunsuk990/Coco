package com.example.coco.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.coco.background.GetCoinPriceRecentContractedWorkManager
import com.example.coco.view.main.MainActivity
import com.example.coco.databinding.ActivitySelectBinding
import com.example.coco.view.adapter.SelectRVAdapter
import timber.log.Timber
import java.util.concurrent.TimeUnit


private const val TAG = "SelectActivity"
class SelectActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectBinding
    private val viewModel: SelectViewModel by viewModels()
    private lateinit var selectRVAdapter: SelectRVAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getCurrentCoinList()

        viewModel.currentPriceResult.observe(this, Observer{
            selectRVAdapter = SelectRVAdapter(this, it)
            binding.selectCoinListRV.adapter = selectRVAdapter
            binding.selectCoinListRV.layoutManager = LinearLayoutManager(this)
            Timber.d(it.toString())
        })

        binding.selectLaterTextArea.setOnClickListener {
            viewModel.setUpFirstFlag()
            viewModel.saveSelectedCoinList(selectRVAdapter.selectedCoinList)
        }

        viewModel.save.observe(this, Observer{
            if(it.equals("done")){
                startActivity(Intent(this, MainActivity::class.java))
                //가장 처음으로 저장한 코인 정보가 저장되는 시점(WorkManager)
                saveinterestCoinDataPeriodic()
            }
        })
    }

    private fun saveinterestCoinDataPeriodic(){
        val myWork = PeriodicWorkRequest.Builder(
            GetCoinPriceRecentContractedWorkManager::class.java,
            15,
            TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "GetCoinPriceRecentContractedWorkManager",
            ExistingPeriodicWorkPolicy.KEEP,
            myWork
        )
    }


}