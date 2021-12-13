package com.example.decor_plant.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.decor_plant.R
import com.google.firebase.auth.FirebaseAuth

class WelcomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        auth = FirebaseAuth.getInstance()

        val btnWelcomeSignUp : Button = findViewById(R.id.btnWelcomeSignUp)
        val btnWelcomeLogIn : Button = findViewById(R.id.btnWelcomeLogIn)

        btnWelcomeSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
        btnWelcomeLogIn.setOnClickListener {
            startActivity(Intent(this, LogInActivity::class.java))
            finish()
        }

    }

    //check if the user already login or not
    override fun onStart() {
        super.onStart()
//        if (auth.currentUser != null && auth.currentUser!!.isEmailVerified) {
//            startActivity(Intent(this, HomePageActivity::class.java))
//            finish()
//        }
        if (auth.currentUser != null) {
            startActivity(Intent(this, HomePageActivity::class.java))
            finish()
        }
    }
}