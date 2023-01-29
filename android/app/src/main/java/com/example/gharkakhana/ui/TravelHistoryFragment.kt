package com.example.gharkakhana.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gharkakhana.ApiViewModel
import com.example.gharkakhana.R
import com.example.gharkakhana.databinding.FragmentTravelHistoryBinding
import com.example.gharkakhana.databinding.ItemTravelHistoryBinding
import com.example.gharkakhana.model.HistoryAdapter
import com.example.gharkakhana.util.Resource

class TravelHistoryFragment : Fragment() {

    private lateinit var binding: FragmentTravelHistoryBinding
    private lateinit var adapter: HistoryAdapter
    private val viewModel:ApiViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTravelHistoryBinding.inflate(LayoutInflater.from(requireContext()), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = HistoryAdapter(listOf())
        binding.rv.adapter = adapter
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        viewModel.getJourneyTransactions(getAuthToken()!!)

        viewModel.journeyTransactionsResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading->{
                    hideRV()
                    showProgressBar()
                }
                is Resource.Success->{
                    hideProgressBar()
                    showRV()
                    Log.d("size",it.data!!.transactions.size.toString())
                    adapter.differ.submitList(it.data.transactions)
                }
                else->{
                    Toast.makeText(requireContext(),"Something went wrong",Toast.LENGTH_LONG).show()
                }
            }
        }

    }
    private fun getAuthToken (): String?{
        val sharedPref = requireActivity().getSharedPreferences("MySharedPref",
            Context.MODE_PRIVATE
        )
        return sharedPref.getString("authToken","")
    }

    private fun showRV(){
        binding.rv.visibility = View.VISIBLE
    }

    private fun hideRV(){
        binding.rv.visibility = View.GONE
    }

    private fun showProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        binding.progressBar.visibility = View.GONE
    }
}