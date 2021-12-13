package com.example.decor_plant.Activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.decor_plant.R
import com.google.firebase.auth.FirebaseAuth

class LogInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var pbLogin: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        auth = FirebaseAuth.getInstance()

        val etEmailLogin: EditText = findViewById(R.id.etEmailLogin)
        val etPasswordLogin: EditText = findViewById(R.id.etPasswordLogin)
        val tvShowPasswordLogin: TextView = findViewById(R.id.tvShowPasswordLogin)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        val tvLoginToForgotPass: TextView = findViewById(R.id.tvLoginToForgotPass)
        val tvLoginToSignUp: TextView = findViewById(R.id.tvLoginToSignUp)
        pbLogin = findViewById(R.id.pbLogin)

        btnLogin.setOnClickListener {
            val email = etEmailLogin.text.toString()
            val password = etPasswordLogin.text.toString()

            if (TextUtils.isEmpty(email)) {
                etEmailLogin.error = "Enter your email here"
                return@setOnClickListener
            }
            if (!isEmailValid(email)) {
                etEmailLogin.error = "Email not valid"
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                etPasswordLogin.error = "Enter a password here"
                return@setOnClickListener
            }
            if (password.length <= 6) {
                etPasswordLogin.error = "Enter password more or equal than 6 character"
                return@setOnClickListener
            }
            pbLogin.visibility = View.VISIBLE
            login(email, password)
        }

        tvShowPasswordLogin.setOnClickListener {
            if (tvShowPasswordLogin.text.toString() == "Show") {
                etPasswordLogin.transformationMethod = HideReturnsTransformationMethod.getInstance()
                tvShowPasswordLogin.text = "Hide"
            } else {
                etPasswordLogin.transformationMethod = PasswordTransformationMethod.getInstance()
                tvShowPasswordLogin.text = "Show"
            }
        }

        tvLoginToForgotPass.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
        tvLoginToSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                pbLogin.visibility = View.GONE

                //check verify email
//                if (auth.currentUser?.isEmailVerified == true) {
//                    Toast.makeText(this, R.string.login_successful, Toast.LENGTH_SHORT).show()
//                    Log.d("LOGIN", "${R.string.login_successful} for ${auth.currentUser?.uid}")
//                    startActivity(Intent(this, HomePageActivity::class.java))
//                    finish()
//                } else {
//                    Toast.makeText(this, R.string.please_verify_email, Toast.LENGTH_LONG).show()
//                }

                //tanpa verify
                Toast.makeText(this, R.string.login_successful, Toast.LENGTH_SHORT).show()
                Log.d("LOGIN", "${R.string.login_successful} for ${auth.currentUser?.uid}")
                startActivity(Intent(this, HomePageActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                pbLogin.visibility = View.GONE
                Toast.makeText(this, "Login failed", Toast.LENGTH_LONG).show()
            }

    }
    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}