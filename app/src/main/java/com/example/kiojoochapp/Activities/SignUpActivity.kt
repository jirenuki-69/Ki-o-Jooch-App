package com.example.kiojoochapp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.WindowManager
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.kiojoochapp.Classes.User
import com.example.kiojoochapp.R
import com.example.kiojoochapp.databinding.ActivityLoginBinding
import com.example.kiojoochapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private var email = ""
    private var name = ""
    private var phoneNumber = ""
    private var address = ""
    private var password = ""
    private var confirmPassword = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Firebase.database.reference

        supportActionBar?.hide()

        firebaseAuth = FirebaseAuth.getInstance()

        listeners()
    }

    private fun listeners() {
        binding.backButton.setOnClickListener {
            finish()
        }

        binding.etEmail.doOnTextChanged { text, _, _, _ ->
            email = text.toString()
        }

        binding.etName.doOnTextChanged { text, _, _, _ ->
            name = text.toString()
        }

        binding.etPhoneNumber.doOnTextChanged { text, _, _, _ ->
            phoneNumber = text.toString()
        }

        binding.etAddress.doOnTextChanged { text, _, _, _ ->
            address = text.toString()
        }

        binding.etPassword.doOnTextChanged { text, _, _, _ ->
            password = text.toString()
        }

        binding.etConfirmPassword.doOnTextChanged { text, _, _, _ ->
            confirmPassword = text.toString()
        }

        binding.btnSignUp.setOnClickListener {
            validateForm()
        }
    }

    private fun validateForm() {
        if (!allFieldsAreFilled()) {
            Toast.makeText(
                this,
                "Favor de llenar todos los campos",
                Toast.LENGTH_SHORT
            ).show()
        } else if (!email.isValidEmail()) {
            Toast.makeText(
                this,
                "Correo electrónico no válido",
                Toast.LENGTH_SHORT
            ).show()
        } else if (password != confirmPassword) {
            Toast.makeText(
                this,
                "Las contraseñas no coinciden",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            createUser()
        }
    }

    private fun createUser() {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = User(name, email, address, phoneNumber)

                    database.child("users")
                        .child(firebaseAuth.currentUser?.uid!!)
                        .setValue(user)
                        .addOnCompleteListener { creationTask ->
                            if (creationTask.isSuccessful) {
                                login()
                            } else {
                                Toast.makeText(
                                    this,
                                    creationTask.exception?.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(
                        this,
                        task.exception?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun login() {
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

    private fun allFieldsAreFilled(): Boolean {
        return email.isNotEmpty() && name.isNotEmpty() && phoneNumber.isNotEmpty() && address.isNotEmpty() && confirmPassword.isNotEmpty() && password.isNotEmpty()
    }

    private fun String.isValidEmail(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
}