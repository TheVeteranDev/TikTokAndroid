package com.example.tiktokandroid.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tiktokandroid.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import androidx.core.graphics.toColorInt
import com.example.tiktokandroid.MainActivity
import kotlin.math.log

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val submitButton: Button = binding.loginSubmitButton
        submitButton.setOnClickListener {
            val email = binding.loginEmailInput.text.toString()
            val password = binding.loginPasswordInput.text.toString()
            signInWithEmailAndPassword(email, password)
        }

        val signUpButton: Button = binding.signupButton
        signUpButton.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // Login function for firebase
    private fun signInWithEmailAndPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Successful login sends user to the main activity
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    // Display toast if authentication fails
                    Toast.makeText(this, "Invalid email and/or password", Toast.LENGTH_LONG).show()
                }
            }
    }
}