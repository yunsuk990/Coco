package com.example.coco.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coco.R
import com.example.coco.databinding.FragmentPriceListBinding

class PriceListFragment : Fragment() {

    private lateinit var binding: FragmentPriceListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPriceListBinding.inflate(inflater, container, false)
        return binding.root
    }


}