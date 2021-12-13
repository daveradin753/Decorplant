package com.example.decor_plant.Activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.decor_plant.Model.Users
import com.example.decor_plant.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomePageActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseFirestore
    private var users = Users()
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        val uid: String = auth.currentUser?.uid.toString()

        getProfile(uid)

        val cvDiagnosisCard: CardView = findViewById(R.id.cvDiagnosisCard)
        val cvFindPlantNameCard: CardView = findViewById(R.id.cvFindPlantNameCard)
        val cvDiagnosisHistoryCard: CardView = findViewById(R.id.cvDiagnosisHistoryCard)

        setSupportActionBar(findViewById(R.id.tbHomePage))

        cvDiagnosisCard.setOnClickListener {
            startActivity(Intent(this, DiagnosisActivity::class.java))
        }
        cvFindPlantNameCard.setOnClickListener {
            startActivity(Intent(this, FindPlantNameActivity::class.java))
        }
        cvDiagnosisHistoryCard.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            intent.putExtra("uid", uid)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_page_actionbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.profile -> {
                val intent = Intent(this, ProfileActivity::class.java)
                intent.putExtra("users", users)
                startActivity(intent)
            }
            R.id.logOut -> {
                logOut()
            }
        }
        return false
    }

    private fun logOut() {
        val builder = AlertDialog.Builder(this)

        builder.setMessage(R.string.logout_dialog)
            .setPositiveButton(R.string.yes) { _, _ ->
                auth.signOut()
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
            .setNegativeButton(R.string.no) { _, _ ->
                Log.d("LOGOUT", R.string.logout_cancelled.toString())
            }
            .show()
    }

    private fun getProfile(uid: String) {
        database.collection("Users").document(uid)
            .get()
            .addOnSuccessListener { documents ->
                if (documents != null) {
                    users = Users(
                        uid = documents.getString("uid"),
                        name = documents.getString("name"),
                        email = documents.getString("email")
                    )
                    Log.d("GET DATA PROFILE", "Document exist")

                } else {
                    Log.e("GET DATA PROFILE", "No such document")
                }

            }
            .addOnFailureListener { e ->
                Log.e("GET DATA PROFILE", "Get data failed", e)
            }
    }

}