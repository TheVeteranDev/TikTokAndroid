package com.example.tiktokandroid.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tiktokandroid.MainActivity
import com.example.tiktokandroid.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val submitButton: Button = binding.signupSubmitButton
        submitButton.setOnClickListener {
            val email = binding.signupEmailInput.text.toString()
            val password = binding.signupPasswordInput.text.toString()
            signUp(email, password)
        }

        val loginButton: Button = binding.signupLoginButton
        loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Successful signup sends user to the main activity
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    // Display toast if signup fails
                    Toast.makeText(this, "Invalid email and/or password", Toast.LENGTH_LONG).show()
                }
        }
    }
}