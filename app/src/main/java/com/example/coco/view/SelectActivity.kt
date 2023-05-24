package com.example.coco.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coco.MainActivity
import com.example.coco.R
import com.example.coco.databinding.ActivitySelectBinding
import com.example.coco.view.adapter.SelectRVAdapter
import timber.log.Timber


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

        viewModel.setUpFirstFlag()

        binding.selectLaterTextArea.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


}