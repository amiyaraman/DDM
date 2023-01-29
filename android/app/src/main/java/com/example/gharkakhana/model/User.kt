package com.example.gharkakhana.model

data class User(
    val __v: Int,
    val _id: String,
    val creationTime: String,
    val email: String,
    val enter: List<Any>,
    val name: String,
    val password: String,
    val phoneNumber: Int,
    val role: Int,
    val sourceStation: String,
    val transactions: List<Any>,
    val wallet: Int,
    val walletTransactions: List<Any>
)