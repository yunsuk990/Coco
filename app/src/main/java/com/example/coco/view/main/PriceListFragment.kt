package com.example.coco.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coco.R
import com.example.coco.databinding.FragmentPriceListBinding
import com.example.coco.view.adapter.PriceListUpDownRVAdapter

class PriceListFragment : Fragment() {

    private lateinit var binding: FragmentPriceListBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPriceListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllSelectedCoinData()
        viewModel.arr15min.observe(viewLifecycleOwner, Observer{
            val priceListUpDownRVAdapter = PriceListUpDownRVAdapter(requireContext(), it)
            binding.price15m.adapter = priceListUpDownRVAdapter
            binding.price15m.layoutManager = LinearLayoutManager(requireContext())
        })
        viewModel.arr30min.observe(viewLifecycleOwner, Observer{
            val priceListUpDownRVAdapter = PriceListUpDownRVAdapter(requireContext(), it)
            binding.price30m.adapter = priceListUpDownRVAdapter
            binding.price30m.layoutManager = LinearLayoutManager(requireContext())
        })
        viewModel.arr45min.observe(viewLifecycleOwner, Observer{
            val priceListUpDownRVAdapter = PriceListUpDownRVAdapter(requireContext(), it)
            binding.price45m.adapter = priceListUpDownRVAdapter
            binding.price45m.layoutManager = LinearLayoutManager(requireContext())
        })
    }




}