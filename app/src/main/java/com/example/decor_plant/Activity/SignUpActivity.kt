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
import com.example.decor_plant.Model.Users
import com.example.decor_plant.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseFirestore
    private lateinit var pbSignUp: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        val etNameSignUp: EditText = findViewById(R.id.etNameSignUp)
        val etEmailSignUp: EditText = findViewById(R.id.etEmailSignUp)
        val etPasswordSignUp: EditText = findViewById(R.id.etPasswordSignUp)
        val etConfirmPasswordSignUp: EditText = findViewById(R.id.etConfirmPasswordSignUp)
        val tvShowPasswordSignUp: TextView = findViewById(R.id.tvShowPasswordSignUp)
        val btnSignUp: Button = findViewById(R.id.btnSignUp)
        val tvSignUpToForgotPass: TextView = findViewById(R.id.tvSignUpToForgotPass)
        val tvSignUpToLogin: TextView = findViewById(R.id.tvSignUpToLogin)
        pbSignUp = findViewById(R.id.pbSignUp)

        btnSignUp.setOnClickListener {
            val name = etNameSignUp.text.toString()
            val email = etEmailSignUp.text.toString()
            val password = etPasswordSignUp.text.toString()
            val confirmPassword = etConfirmPasswordSignUp.text.toString()

            if (TextUtils.isEmpty(name)) {
                etNameSignUp.error = "Enter your name here"
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(email)) {
                etEmailSignUp.error = "Enter your email here"
                return@setOnClickListener
            }
            if (!isEmailValid(email)) {
                etEmailSignUp.error = "Email not valid"
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                etPasswordSignUp.error = "Enter a password here"
                return@setOnClickListener
            }
            if (password.length <= 6) {
                etPasswordSignUp.error = "Enter password more or equal than 6 character"
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(confirmPassword)) {
                etConfirmPasswordSignUp.error = "Confirm your password"
                return@setOnClickListener
            }
            if (password != confirmPassword) {
                etPasswordSignUp.error = "Those passwords didn’t match. Try again."
                etConfirmPasswordSignUp.setText("")
                return@setOnClickListener
            }
            pbSignUp.visibility = View.VISIBLE
            signUp(name, email, password)
        }

        tvShowPasswordSignUp.setOnClickListener {
            if (tvShowPasswordSignUp.text.toString() == "Show") {
                etPasswordSignUp.transformationMethod = HideReturnsTransformationMethod.getInstance()
                tvShowPasswordSignUp.text = "Hide"
            } else {
                etPasswordSignUp.transformationMethod = PasswordTransformationMethod.getInstance()
                tvShowPasswordSignUp.text = "Show"
            }
        }

        tvSignUpToForgotPass.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
            finish()
        }

        tvSignUpToLogin.setOnClickListener {
            startActivity(Intent(this, LogInActivity::class.java))
            finish()
        }

    }

    private fun signUp(name: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
//                auth.currentUser?.sendEmailVerification()
                val uid = auth.currentUser?.uid.toString()
                val user = Users(uid, email, name)
                database.collection("Users").document(uid).set(user)
                    .addOnSuccessListener {
                        pbSignUp.visibility = View.GONE
//                        Toast.makeText(this, R.string.registration_successful, Toast.LENGTH_LONG).show()
                        Log.d("SIGN UP", "Registration with $uid successful")
                        auth.signOut()
                        startActivity(Intent(this, WelcomeActivity::class.java))
                        finish()
                    }
                    .addOnFailureListener { e->
                        pbSignUp.visibility = View.GONE
                        Toast.makeText(this, R.string.registration_failed, Toast.LENGTH_SHORT).show()
                        Log.e("SIGN UP", "Signup failed", e)
                    }
            }
            .addOnFailureListener { e->
                pbSignUp.visibility = View.GONE
                Toast.makeText(this, R.string.registration_failed, Toast.LENGTH_SHORT).show()
                Log.e("SIGN UP", "Registration failed", e)
            }
    }
    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}