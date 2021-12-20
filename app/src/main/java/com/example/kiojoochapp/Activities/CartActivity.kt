package com.example.kiojoochapp.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kiojoochapp.R
import com.example.kiojoochapp.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btnCartBack.setOnClickListener {
            finish()
        }

        binding.btnProceedPayment.setOnClickListener {
            val intent = Intent(this as Context, PaymentActivity::class.java)

            startActivity(intent)
        }

        binding.btnCartAccount.setOnClickListener {
            val intent = Intent(this as Context, AccountActivity::class.java)

            startActivity(intent)
        }
    }
}