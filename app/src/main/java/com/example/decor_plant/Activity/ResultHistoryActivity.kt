package com.example.decor_plant.Activity

import android.os.Bundle
import android.text.Html
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.decor_plant.Model.History
import com.example.decor_plant.R

class ResultHistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_history)

        val history: History? = intent.getParcelableExtra<History>("history")

        val btnBackResultHistory: ImageButton = findViewById(R.id.btnBackResultHistory)
        val ivResultHistoryImage: ImageView = findViewById(R.id.ivResultHistoryImage)
        val tvResultHistoryDiseaseName: TextView = findViewById(R.id.tvResultHistoryDiseaseName)
        val tvHistoryResultDate: TextView = findViewById(R.id.tvHistoryResultDate)
        val tvResultHistoryDiseaseSolution: TextView = findViewById(R.id.tvResultHistoryDiseaseSolution)

        ivResultHistoryImage.load(history?.image) {
            crossfade(true)
            placeholder(R.drawable.icon_result_test)
        }
        tvResultHistoryDiseaseName.text = history?.name
        tvHistoryResultDate.text = history?.date
        tvResultHistoryDiseaseSolution.text = Html.fromHtml(history?.solution)

        btnBackResultHistory.setOnClickListener {
            finish()
        }
    }
}