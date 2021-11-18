package com.example.kiojoochapp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.kiojoochapp.AuthActivity
import com.example.kiojoochapp.Classes.User
import com.example.kiojoochapp.R
import com.example.kiojoochapp.databinding.ActivityHomeBinding
import com.example.kiojoochapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        firebaseAuth = FirebaseAuth.getInstance()

        loadUser()
        listeners()
    }

    private fun loadUser() {

        Log.d(AuthActivity.AUTH_DEBUG_TEXT, "${firebaseAuth.currentUser?.displayName}")
        binding.tvUsername.text = firebaseAuth.currentUser?.email
    }

    private fun listeners() {
        binding.btnSignOut.setOnClickListener {
            firebaseAuth.signOut()

            if (isTaskRoot) {
                val intent = Intent(this, LoginActivity::class.java)

                startActivity(intent)
                finish()
            } else {
                finish()
            }
        }
    }
}