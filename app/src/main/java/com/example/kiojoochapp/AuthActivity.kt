package com.example.kiojoochapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import com.example.kiojoochapp.Activities.HomeActivity
import com.example.kiojoochapp.Activities.LoginActivity
import com.example.kiojoochapp.Activities.SignUpActivity
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : AppCompatActivity() {

    companion object {
        const val AUTH_DEBUG_TEXT = "AUTH_DEBUG"
    }

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        supportActionBar?.hide()

        configAuth()
    }

    private fun configAuth() {
        firebaseAuth = FirebaseAuth.getInstance()
        authStateListener = FirebaseAuth.AuthStateListener { auth ->
            if (auth.currentUser != null) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivityForResult(intent, LoginActivity.AUTH_REQUEST_CODE)
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivityForResult(intent, LoginActivity.AUTH_REQUEST_CODE)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        firebaseAuth.addAuthStateListener(authStateListener)
    }

    override fun onPause() {
        super.onPause()
        firebaseAuth.removeAuthStateListener(authStateListener)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_CANCELED) {
            finish()
        }
    }
}