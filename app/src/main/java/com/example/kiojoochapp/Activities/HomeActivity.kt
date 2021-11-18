package com.example.kiojoochapp.Activities

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

        loadUser()
    }

    private fun loadUser() {
        firebaseAuth = FirebaseAuth.getInstance()
        database = Firebase.database.reference
        database
            .child("users")
            .child(firebaseAuth.currentUser!!.uid).get()
            .addOnSuccessListener { snapshot ->
                val user = snapshot.value as User
                binding.tvUsername.text = user.name
            }
    }
}