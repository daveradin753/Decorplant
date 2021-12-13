package com.example.decor_plant.Activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.decor_plant.R
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var etEmailForgotPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        auth = FirebaseAuth.getInstance()

        etEmailForgotPassword = findViewById(R.id.etEmailForgotPassword)
        val btnSendEmailForgotPassword: Button = findViewById(R.id.btnSendEmailForgotPassword)
        val tvForgotPasswordToLogin: TextView = findViewById(R.id.tvForgotPasswordToLogin)

        btnSendEmailForgotPassword.setOnClickListener {
            val email = etEmailForgotPassword.text.toString()

            if (TextUtils.isEmpty(email)) {
                etEmailForgotPassword.error = "Enter your email here"
                return@setOnClickListener
            }
            if (!isEmailValid(email)) {
                etEmailForgotPassword.error = "Email not valid"
                return@setOnClickListener
            }
            sendEmailForgotPassword(email)
        }

        tvForgotPasswordToLogin.setOnClickListener {
            startActivity(Intent(this, LogInActivity::class.java))
            finish()
        }
    }

    private fun sendEmailForgotPassword(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                Toast.makeText(this, R.string.email_reset_pass_sent, Toast.LENGTH_SHORT).show()
                etEmailForgotPassword.setText("")
            }
            .addOnFailureListener { e->
                Toast.makeText(this, R.string.failed_send_email, Toast.LENGTH_LONG).show()
                Log.e("FORGOT PASSWORD", R.string.failed_send_email.toString(), e)
            }
    }
    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}