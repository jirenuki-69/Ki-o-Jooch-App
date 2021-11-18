package com.example.kiojoochapp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.widget.doOnTextChanged
import com.example.kiojoochapp.AuthActivity
import com.example.kiojoochapp.Classes.User
import com.example.kiojoochapp.R
import com.example.kiojoochapp.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    companion object {
        const val GOOGLE_SIGN_IN_REQUEST_CODE = 100
        const val AUTH_REQUEST_CODE = 60
    }

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var binding: ActivityLoginBinding
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        database = Firebase.database.reference

        email = ""
        password = ""

        listeners()
    }

    private fun listeners() {
        binding.etEmail.doOnTextChanged { text, _, _, _ ->
            email = text.toString()
        }

        binding.etPassword.doOnTextChanged { text, _, _, _ ->
            password = text.toString()
        }

        binding.btnLogin.setOnClickListener {
            validateForm()
        }

        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.btnGoogle.setOnClickListener {
            googleSignIn()
        }
    }

    private fun validateForm() {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                this,
                "Favor de llenar todos los campos",
                Toast.LENGTH_SHORT
            ).show()
        } else if (!email.isValidEmail()) {
            Toast.makeText(
                this,
                "Correo no válido",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            emailAndPasswordSignIn()
        }
    }

    private fun emailAndPasswordSignIn() {
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this,
                        task.exception?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun googleSignIn() {
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleClient = GoogleSignIn.getClient(this, googleConf)
        googleClient.signOut()

        startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN_REQUEST_CODE)
    }

    private fun manageUserInformationFromGoogleSignIn(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)

        try {
            val account = task.getResult(ApiException::class.java)

            if (account != null) {
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                firebaseAuth = FirebaseAuth.getInstance()
                firebaseAuth.signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = User(account.displayName, account.email, "", "")
                            database
                                .child("users")
                                .child(firebaseAuth.currentUser!!.uid)
                                .setValue(user)
                                .addOnCompleteListener { creationTask ->
                                    if (creationTask.isSuccessful) {
                                        val intent = Intent(this, HomeActivity::class.java)
                                        startActivity(intent)
                                    }
                                }
                        } else {
                            Toast.makeText(
                                this,
                                "No se pudo completar el inicio de sesión con Google, pruebe otro método",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        } catch (error: ApiException) {

        }
    }

    private fun String.isValidEmail(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            GOOGLE_SIGN_IN_REQUEST_CODE -> {
                if (resultCode == RESULT_OK) {
                    manageUserInformationFromGoogleSignIn(
                        data)
                } else {
                    Toast.makeText(
                        this,
                        "Favor de llenar todos los campos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            AUTH_REQUEST_CODE -> {
                if (resultCode == RESULT_OK) {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivityForResult(intent, 1)
                }
            }
            else -> {
                firebaseAuth = FirebaseAuth.getInstance()
                firebaseAuth.signOut()
            }
        }
    }
}