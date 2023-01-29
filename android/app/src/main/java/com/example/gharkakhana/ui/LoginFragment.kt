package com.example.gharkakhana.ui

import android.content.Context.MODE_APPEND
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.gharkakhana.ApiViewModel
import com.example.gharkakhana.R
import com.example.gharkakhana.databinding.FragmentLoginBinding
import com.example.gharkakhana.util.Resource

class LoginFragment : Fragment() {

    private lateinit var binding : FragmentLoginBinding
    private lateinit var  viewModel: ApiViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(LayoutInflater.from(requireContext()),container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ApiViewModel::class.java]

        binding.loginBtn.setOnClickListener {
            val email = binding.emailText.text.toString()
            val password = binding.passwordText.text.toString()

            viewModel.signIn(email,password)
        }

        viewModel.signInResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {
                    hideLoginButton()
                    showProgressBar()
                }
                is Resource.Success ->{
                    it.data?.let { response->
                        Log.d("response",response.token)
                        saveAuthToken(response.token)
                        navigateToNext()
                    }
                }
                else ->{
                    Toast.makeText(requireContext(),"Couldn't Login",Toast.LENGTH_LONG).show()
                    hideProgressBar()
                    showLoginButton()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val token  = getAuthToken()
        token?.let {
            if(it.isNotEmpty()){
                navigateToNext()
            }
        }

    }
    private fun saveAuthToken(token: String) {
        val sharedPref = requireActivity().getSharedPreferences("MySharedPref",MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("authToken",token)
        editor.apply()
    }

    private fun getAuthToken (): String?{
        val sharedPref = requireActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE)
        return sharedPref.getString("authToken","")
    }

    private fun navigateToNext(){
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
    }

    private fun showLoginButton(){
        binding.loginBtn.visibility = View.VISIBLE
    }

    private fun hideLoginButton(){
        binding.loginBtn.visibility = View.GONE
    }

    private fun showProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        binding.progressBar.visibility = View.GONE
    }

}