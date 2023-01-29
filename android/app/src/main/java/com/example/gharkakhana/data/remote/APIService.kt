package com.example.gharkakhana.data.remote

import com.example.gharkakhana.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface APIService {

    @POST("api/auth/signin")
    suspend fun signin(
        @Body user:HashMap<String,String>
    ):Response<SignInResponse>

    @POST("api/auth/signup")
    suspend fun signUp(
        @Query("email")email:String,
        @Query("password")password:String,
        @Query("name")name:String,
        @Header("Authorization")auth_token:String,
    ):Response<SignUpResponse>

    @GET("api/user")
    suspend fun getUser(
        @Header("Authorization")auth_token:String,
    ):Response<User>

    @GET("api/user/journeyTransactions")
    suspend fun getJourneyTransactions(
        @Header("Authorization")auth_token:String,
    ):Response<JourneyTransactionsResponse>

    @POST("api/user/updateJourneyStatus")
    suspend fun updateJourneyStatus(
        @Body data:QRInfo,
        @Header("Authorization")auth_token:String,
    ):Response<UpdateJourneyStatusResponse>


}