package com.example.gharkakhana.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gharkakhana.ApiViewModel
import com.example.gharkakhana.R
import com.example.gharkakhana.databinding.FragmentHomeBinding
import com.example.gharkakhana.util.Resource

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel:ApiViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(LayoutInflater.from(requireContext()),container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rechargeImage.setOnClickListener{
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToRechargeFragment())
        }
        binding.navigatorImage.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNavigatorFragment())
        }
        binding.scannerImage.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToScannerFragment())
        }
        binding.travelHistoryImage.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToTravelHistoryFragment())
        }
        binding.recommendedArticlesImage.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSubscriptionsFragment())
        }
        viewModel.getUser(getAuthToken()!!)

        viewModel.userResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading->{
                    showProgressBar()
                    hideBalance()
                }
                is Resource.Success->{
                    binding.walletBalanceTxt.text = "₹ ${it.data!!.wallet.toString()}"
                    hideProgressBar()
                    showBalance()
                }
                else->{
                    showBalance()
                    hideProgressBar()
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
    private fun showBalance(){
        binding.walletBalanceTxt.visibility = View.VISIBLE
    }

    private fun hideBalance(){
        binding.walletBalanceTxt.visibility = View.GONE
    }

    private fun showProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        binding.progressBar.visibility = View.GONE
    }
}