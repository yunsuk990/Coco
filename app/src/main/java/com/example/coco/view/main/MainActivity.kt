package com.example.coco.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coco.R
import timber.log.Timber

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.d("MainActivivty")

    }
}