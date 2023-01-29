package com.example.gharkakhana.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.gharkakhana.R
import com.example.gharkakhana.databinding.FragmentRechargeBinding
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject
import kotlin.math.roundToInt

class RechargeFragment : Fragment() {
    private lateinit var binding: FragmentRechargeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRechargeBinding.inflate(LayoutInflater.from(requireContext()),container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.payBtn.setOnClickListener {
            val checkout = Checkout()
            checkout.setKeyID("rzp_test_gxyGvrgcCDtc4O")
            val samount = binding.amount.text.toString();

            // rounding off the amount.
            val amount = (samount.toFloat()).roundToInt()*100
            Toast.makeText(requireContext(),amount.toString(),Toast.LENGTH_LONG).show()
            val obj = JSONObject()
            try {
                // to put name
                obj.put("name", "DMRC")

                // put description
                obj.put("description", "Test payment");

                // to set theme color
                obj.put("theme.color", "#5b8bdf");

                // put the currency
                obj.put("currency", "INR");

                // put amount
                obj.put("amount",amount);

                // put mobile number
                obj.put("prefill.contact", "9284064503");

                // put email
                obj.put("prefill.email", "mishrashashank501@gmail.com");

                // open razorpay to checkout activity
                checkout.open(requireActivity(),obj)
            } catch (exc:Exception){
                Toast.makeText(requireContext(),"Payment Could not be completed . Please try again",Toast.LENGTH_LONG).show()
            }
        }
    }


}