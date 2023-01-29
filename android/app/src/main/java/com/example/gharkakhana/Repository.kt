package com.example.gharkakhana

import com.example.gharkakhana.data.remote.APIService
import com.example.gharkakhana.model.*
import com.example.gharkakhana.util.Resource

class Repository(
    private val api:APIService
) {
    suspend fun signin(
        email: String,
        password:String,
    ): Resource<SignInResponse>{
        val user = HashMap<String,String>()
        user["email"]=email
        user["password"]=password
        val response = api.signin(user)
        if(response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }?:return Resource.Error("Something went wrong")
        }else{
            return Resource.Error("Something went wrong")
        }
    }

    suspend fun signup(
        email: String,
        password:String,
        name:String,
        authToken:String
    ): Resource<SignUpResponse>{
        val response = api.signUp(email,password,name,authToken)
        if(response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }?:return Resource.Error("Something went wrong")
        }else{
            return Resource.Error("Something went wrong")
        }
    }

    suspend fun getUser(
        authToken:String
    ): Resource<User>{
        val finalToken = "Bearer $authToken"
        val response = api.getUser(finalToken)
        if(response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }?:return Resource.Error("Something went wrong")
        }else{
            return Resource.Error("Something went wrong")
        }
    }

    suspend fun getJourneyTransactions(
        authToken:String
    ): Resource<JourneyTransactionsResponse>{
        val finalToken = "Bearer $authToken"
        val response = api.getJourneyTransactions(finalToken)
        if(response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }?:return Resource.Error("Something went wrong")
        }else{
            return Resource.Error("Something went wrong")
        }
    }

    suspend fun updateJourneyStatus(
        data:QRInfo,
        authToken:String
    ): Resource<UpdateJourneyStatusResponse>{
        val finalToken = "Bearer $authToken"
        val response = api.updateJourneyStatus(data,finalToken)
        if(response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }?:return Resource.Error("Something went wrong")
        }else{
            return Resource.Error("Something went wrong")
        }
    }


}