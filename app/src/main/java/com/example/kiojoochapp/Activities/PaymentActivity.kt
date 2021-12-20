package com.example.kiojoochapp.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kiojoochapp.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnPay.setOnClickListener {
            finish()
        }
    }
}