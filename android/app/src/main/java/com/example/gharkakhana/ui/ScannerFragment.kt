package com.example.gharkakhana.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.example.gharkakhana.ApiViewModel
import com.example.gharkakhana.databinding.FragmentScannerBinding
import com.example.gharkakhana.model.QRInfo
import com.example.gharkakhana.util.Resource
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class ScannerFragment : Fragment() {

    private lateinit var binding: FragmentScannerBinding
    private lateinit var scanner : CodeScanner
    private val viewModel:ApiViewModel  by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentScannerBinding.inflate(LayoutInflater.from(requireContext()),container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf( Manifest.permission.CAMERA) ,100);
        }

        scanner = CodeScanner(requireActivity(),binding.camera)
        scanner.decodeCallback = DecodeCallback {
            requireActivity().runOnUiThread{
                hideCamera()
                val data = GsonBuilder().create().fromJson(it.text,QRInfo::class.java)
                viewModel.updateJourneyStatus(data,getAuthToken()!!)
            }
        }

        viewModel.updateJourneyStatusResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading->{
                    hideCamera()
                    showProgressBar()
                }
                is Resource.Success->{
                    Toast.makeText(requireContext(),it.data!!.message,Toast.LENGTH_LONG).show()
                    findNavController().navigate(ScannerFragmentDirections.actionScannerFragmentToHomeFragment())
                }
                else->{
                    hideProgressBar()
                    showCamera()
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

    private fun showCamera(){
        scanner.startPreview()
        binding.camera.visibility = View.VISIBLE
    }
    private fun hideCamera(){
        scanner.releaseResources()
        binding.camera.visibility = View.GONE
    }

    private fun showButton(){
        binding.startJourneyButton.visibility = View.VISIBLE
    }
    private fun hideButton(){
        binding.startJourneyButton.visibility = View.GONE
    }

    private fun showProgressBar(){
        binding.progressBar2.visibility = View.VISIBLE
    }
    private fun hideProgressBar(){
        binding.progressBar2.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        scanner.startPreview()
    }

    override fun onPause() {
        scanner.releaseResources()
        super.onPause()
    }
}