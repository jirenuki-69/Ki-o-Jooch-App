package com.example.kiojoochapp.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
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

    private val resultLauncher = registerForActivityResult(StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            Toast.makeText(
                this as Context,
                "Cambios Aplicados",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

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

//        Log.d(AuthActivity.AUTH_DEBUG_TEXT, "${firebaseAuth.currentUser?.displayName}")
//        binding.tvUsername.text = firebaseAuth.currentUser?.email
    }

    private fun listeners() {
//        binding.btnSignOut.setOnClickListener {
//            firebaseAuth.signOut()
//
//            if (isTaskRoot) {
//                val intent = Intent(this, LoginActivity::class.java)
//
//                startActivity(intent)
//                finish()
//            } else {
//                finish()
//            }
//        }

        binding.imgUser.setOnClickListener {
            // TODO: Go to Account Activity
            val intent = Intent(this as Context, AccountActivity::class.java)
            resultLauncher.launch(intent)
        }

        binding.fabCart.setOnClickListener {
            // TODO: Go to Shopping Cart Activity
            val intent = Intent(this as Context, CartActivity::class.java)
            resultLauncher.launch(intent)
        }

        binding.btnChiles.setOnClickListener {
            val intent = Intent(this as Context, CatalogActivity::class.java)

            startActivity(intent)
        }

        binding.btnEncurtidos.setOnClickListener {
            val intent = Intent(this as Context, CatalogActivity::class.java)

            startActivity(intent)
        }

        binding.producto.setOnClickListener {
            val intent = Intent(this as Context, ProductDetailsActivity::class.java)

            startActivity(intent)
        }
    }
}