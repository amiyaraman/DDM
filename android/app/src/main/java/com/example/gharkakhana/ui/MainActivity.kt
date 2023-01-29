package com.example.gharkakhana.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.gharkakhana.R
import com.razorpay.PaymentResultListener

class MainActivity : AppCompatActivity(),PaymentResultListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this,"Success", Toast.LENGTH_LONG).show()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this,"Payment Could not be completed . Please try again", Toast.LENGTH_LONG).show()
    }
}