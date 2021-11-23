package com.example.kiojoochapp.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kiojoochapp.R
import com.example.kiojoochapp.databinding.ActivityAccountBinding
import com.google.firebase.auth.FirebaseAuth

class AccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        firebaseAuth = FirebaseAuth.getInstance()

        listeners()
    }

    private fun listeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnSave.setOnClickListener {
            // TODO: Save info
            setResult(RESULT_OK)
            finish()
        }

        binding.btnLogout.setOnClickListener {
            // TODO: Logout
            firebaseAuth.signOut()

            val intent = Intent(this as Context, LoginActivity::class.java)
            finishAffinity()
            startActivity(intent)
        }
    }
}