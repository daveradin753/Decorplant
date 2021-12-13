package com.example.decor_plant.Activity

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.decor_plant.Model.Disease
import com.example.decor_plant.R

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val tvResultDiseaseName: TextView = findViewById(R.id.tvResultDiseaseName)
        val tvResultDiseaseSolution: TextView = findViewById(R.id.tvResultDiseaseSolution)
        val btnBackResult: ImageButton = findViewById(R.id.btnBackResult)
        val ivResultImage: ImageView = findViewById(R.id.ivResultImage)

        val disease: Disease? = intent.getParcelableExtra<Disease>("disease")
        Log.d("RESULT DATA", disease?.name.toString())

        tvResultDiseaseName.text = disease?.name.toString()
        tvResultDiseaseSolution.text = Html.fromHtml(disease?.solution.toString())
        ivResultImage.load(disease?.image) {
            crossfade(true)
            placeholder(R.drawable.icon_result_test)
        }

        btnBackResult.setOnClickListener {
            finish()
        }
    }
}