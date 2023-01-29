package com.example.gharkakhana

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gharkakhana.data.remote.RetrofitInstance
import com.example.gharkakhana.model.*
import com.example.gharkakhana.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class ApiViewModel: ViewModel() {

    private var repository: Repository = Repository(RetrofitInstance.api)


    private val _signInResponse = MutableLiveData<Resource<SignInResponse>>()
    val signInResponse : LiveData<Resource<SignInResponse>>
    get() = _signInResponse

    private val _updateJourneyStatusResponse = MutableLiveData<Resource<UpdateJourneyStatusResponse>>()
    val updateJourneyStatusResponse : LiveData<Resource<UpdateJourneyStatusResponse>>
    get() = _updateJourneyStatusResponse

    private val _journeyTransactionsResponse = MutableLiveData<Resource<JourneyTransactionsResponse>>()
    val journeyTransactionsResponse : LiveData<Resource<JourneyTransactionsResponse>>
    get() = _journeyTransactionsResponse

    private val _userResponse = MutableLiveData<Resource<User>>()
    val userResponse : LiveData<Resource<User>>
    get() = _userResponse

    fun signIn(email:String,password:String){
        _signInResponse.value = Resource.Loading()
        viewModelScope.launch {
            _signInResponse.value = repository.signin(email,password)
        }
    }

    fun updateJourneyStatus(data:QRInfo,authToken:String){
        _updateJourneyStatusResponse.value = Resource.Loading()
        viewModelScope.launch {
            _updateJourneyStatusResponse.value = repository.updateJourneyStatus(data,authToken)
        }
    }

    fun getJourneyTransactions(authToken: String){
        _journeyTransactionsResponse.value = Resource.Loading()
        viewModelScope.launch {
           _journeyTransactionsResponse.value = repository.getJourneyTransactions(authToken)
        }
    }

    fun getUser(authToken: String){
        _userResponse.value = Resource.Loading()
        viewModelScope.launch {
            _userResponse.value = repository.getUser(authToken)
        }
    }

}