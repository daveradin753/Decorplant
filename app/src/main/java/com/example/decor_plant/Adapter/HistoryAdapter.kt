package com.example.decor_plant.Adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.decor_plant.Activity.ResultHistoryActivity
import com.example.decor_plant.Model.History
import com.example.decor_plant.R
import com.google.firebase.firestore.FirebaseFirestore

class HistoryAdapter() : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    var historyData : ArrayList<History> = ArrayList()
    private lateinit var database: FirebaseFirestore

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivHistoryImage: ImageView = view.findViewById(R.id.ivHistoryImage)
        val tvHistoryNameDisease: TextView = view.findViewById(R.id.tvHistoryNameDisease)
        val tvHistoryDate: TextView = view.findViewById(R.id.tvHistoryDate)
        val btnHistoryDelete: ImageButton = view.findViewById(R.id.btnHistoryDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history_list, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.ivHistoryImage.load(historyData[position].image) {
            crossfade(true)
            placeholder(R.drawable.icon_result_test)
        }
        holder.tvHistoryNameDisease.text = historyData[position].name
        holder.tvHistoryDate.text = historyData[position].date
        holder.btnHistoryDelete.setOnClickListener {
            deleteHistoryDatabase(historyData[position].id.toString())
            historyData.removeAt(position)
            notifyDataSetChanged()
        }
        holder.itemView.setOnClickListener {
            val history: History = historyData[position]
            val intent = Intent(holder.itemView.context, ResultHistoryActivity::class.java)
            intent.putExtra("history", history)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return historyData.size
    }

    private fun deleteHistoryDatabase(id: String) {
        database = FirebaseFirestore.getInstance()
        database.collection("history").document(id)
            .delete()
            .addOnSuccessListener {
                Log.d("DELETE HISTORY", "Delete history success")
            }
            .addOnFailureListener { e ->
                Log.e("DELETE HISTORY", "Delete history failed | ", e)
            }
    }
}