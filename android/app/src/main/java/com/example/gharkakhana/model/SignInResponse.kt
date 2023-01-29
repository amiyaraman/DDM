package com.example.gharkakhana.model

data class SignInResponse(
    val success: Boolean,
    val token: String,
    val user: User
)