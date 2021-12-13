package com.example.decor_plant.Activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.decor_plant.Adapter.HistoryAdapter
import com.example.decor_plant.Model.History
import com.example.decor_plant.R
import com.google.firebase.firestore.FirebaseFirestore

class HistoryActivity : AppCompatActivity() {

    private lateinit var database: FirebaseFirestore
    private var historyData: ArrayList<History> = ArrayList()
    private lateinit var tvHistoryEmpty: TextView
    private lateinit var rvHistory: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        database = FirebaseFirestore.getInstance()
        val uid = intent.getStringExtra("uid")

        rvHistory = findViewById(R.id.rvHistory)
        tvHistoryEmpty = findViewById(R.id.tvHistoryEmpty)
        val btnBackHistory: ImageButton = findViewById(R.id.btnBackHistory)

        getHistoryData(uid.toString())

        btnBackHistory.setOnClickListener {
            finish()
        }
    }

    private fun getHistoryData(uid: String){
        historyData.clear()
        database.collection("history")
            .whereEqualTo("uid", uid)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    historyData.add(History(
                        id = document.getString("id"),
                        uid = document.getString("uid"),
                        name = document.getString("name"),
                        image = document.getString("image"),
                        date = document.getString("date"),
                        solution = document.getString("solution")

                    ))

                    val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    val adapter = HistoryAdapter()

                    historyData.sortBy { it.date }

                    adapter.historyData = historyData

                    rvHistory.layoutManager = layoutManager
                    rvHistory.setHasFixedSize(true)
                    rvHistory.adapter = adapter

                }
                if (historyData.isEmpty()) {
                    tvHistoryEmpty.visibility = View.VISIBLE
                } else {
                    tvHistoryEmpty.visibility = View.INVISIBLE

                }

                Log.d("GET DATA HISTORY", "Get data history success")
            }
            .addOnFailureListener { e ->
                Log.e("GET DATA HISTORY", "Get data history failed", e)
            }
    }

}