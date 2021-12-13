package com.example.decor_plant.Activity

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.decor_plant.Model.Users
import com.example.decor_plant.R

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val tvProfileName: TextView = findViewById(R.id.tvProfileName)
        val tvProfileEmail: TextView = findViewById(R.id.tvProfileEmail)
        val btnBackProfile: ImageButton = findViewById(R.id.btnBackProfile)

        val users: Users? = intent.getParcelableExtra<Users>("users")

        tvProfileName.text = users?.name.toString()
        tvProfileEmail.text = users?.email.toString()

        btnBackProfile.setOnClickListener {
            finish()
        }
    }
}