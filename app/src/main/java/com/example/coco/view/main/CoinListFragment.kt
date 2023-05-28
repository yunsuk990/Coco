package com.example.coco.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.util.query
import com.example.coco.R
import com.example.coco.databinding.FragmentCoinListBinding
import com.example.coco.db.entity.InterestCoinEntity
import com.example.coco.view.adapter.CoinListRVAdapter
import timber.log.Timber

class CoinListFragment : Fragment() {

    lateinit var binding: FragmentCoinListBinding
    private val viewModel: MainViewModel by activityViewModels()

    private val selectedList = ArrayList<InterestCoinEntity>()
    private val unselectedList = ArrayList<InterestCoinEntity>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCoinListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllInterestCoinData()
        viewModel.selectedCoinList.observe(viewLifecycleOwner, Observer{
            //Timber.d(it.toString())
            selectedList.clear()
            unselectedList.clear()

            for(item in it){
                if(item.selected){
                    selectedList.add(item)
                }else{
                    unselectedList.add(item)
                }
            }

            setSelectedListRV()
        })
    }

    private fun setSelectedListRV(){
        val selectedRVAdapter = CoinListRVAdapter(requireContext(), selectedList)
        binding.selectedCoinRV.adapter = selectedRVAdapter
        binding.selectedCoinRV.layoutManager = LinearLayoutManager(requireContext())

        selectedRVAdapter.itemClick = object: CoinListRVAdapter.ItemClick{
            override fun onClick(view: View, position: Int) {
                viewModel.updateInterestCoinData(selectedList[position])
            }

        }

        val unselectedRVAdapter = CoinListRVAdapter(requireContext(), unselectedList)
        binding.unselectedCoinRV.adapter = unselectedRVAdapter
        binding.unselectedCoinRV.layoutManager = LinearLayoutManager(requireContext())

        unselectedRVAdapter.itemClick = object: CoinListRVAdapter.ItemClick{
            override fun onClick(view: View, position: Int) {
                viewModel.updateInterestCoinData(unselectedList[position])

            }

        }
    }

}