package com.example.kiojoochapp.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kiojoochapp.databinding.ActivityCatalogBinding

class CatalogActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCatalogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatalogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnAccount.setOnClickListener {
            val intent = Intent(this as Context, AccountActivity::class.java)

            startActivity(intent)
        }

        binding.producto.setOnClickListener {
            val intent = Intent(this as Context, ProductDetailsActivity::class.java)

            startActivity(intent)
        }
    }
}