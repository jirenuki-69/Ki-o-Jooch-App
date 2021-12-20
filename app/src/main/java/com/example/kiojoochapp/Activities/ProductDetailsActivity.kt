package com.example.kiojoochapp.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kiojoochapp.databinding.ActivityProductDetailsBinding

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnProceedPayment.setOnClickListener {
            finish()
        }
    }
}