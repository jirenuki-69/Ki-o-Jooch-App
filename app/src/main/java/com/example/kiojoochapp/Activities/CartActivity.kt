package com.example.kiojoochapp.Activities

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
    }
}