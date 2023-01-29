package com.example.gharkakhana.model

data class SignUpResponse(
    val email: String,
    val name: String,
    val password: String,
    val success: Boolean
)