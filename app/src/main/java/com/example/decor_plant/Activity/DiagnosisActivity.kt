package com.example.decor_plant.Activity

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.decor_plant.Adapter.SymptomsAdapter
import com.example.decor_plant.Model.Disease
import com.example.decor_plant.Model.History
import com.example.decor_plant.Model.getDataSymtomps
import com.example.decor_plant.R
import com.example.decor_plant.databinding.DialogSymptomsResultBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class DiagnosisActivity : AppCompatActivity(), SymptomsAdapter.Interaction {

    private lateinit var database: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private var disease = Disease()
    private var history = History()
    private lateinit var adapter: SymptomsAdapter
    private lateinit var dialog: Dialog
    private lateinit var dialogBinding: DialogSymptomsResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diagnosis)

        database = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        val rvDiagnosis: RecyclerView = findViewById(R.id.rvDiagnosis)
        val btnSubmitDiagnosis: Button = findViewById(R.id.btnSubmitDiagnosis)
        val btnBackDiagnosis: ImageButton = findViewById(R.id.btnBackDiagnosis)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = SymptomsAdapter(this)
        adapter.symtomsData = getDataSymtomps()

        rvDiagnosis.layoutManager = layoutManager
        rvDiagnosis.setHasFixedSize(true)
        rvDiagnosis.adapter = adapter

        val calender = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        var date: String = dateFormat.format(calender.time)

//        val dialogView = View.inflate(this, R.layout.dialog_symptoms_result, null)
//
//        val builder = AlertDialog.Builder(this)
//        builder.setView(dialogView)
//
//        val dialogSymptoms = builder.create()

        btnSubmitDiagnosis.setOnClickListener {
            var historyId: String
            if (adapter.symtomsData[0].status == true && adapter.symtomsData[1].status == true && adapter.symtomsData[2].status == false && adapter.symtomsData[3].status == false && adapter.symtomsData[4].status == false && adapter.symtomsData[5].status == false && adapter.symtomsData[6].status == false && adapter.symtomsData[7].status == false && adapter.symtomsData[8].status == false && adapter.symtomsData[9].status == false && adapter.symtomsData[10].status == false && adapter.symtomsData[11].status == false && adapter.symtomsData[12].status == false && adapter.symtomsData[13].status == false && adapter.symtomsData[14].status == false) {
                historyId = UUID.randomUUID().toString()
                getDataDisease("p0", historyId, date)
            } else if (adapter.symtomsData[1].status == true && adapter.symtomsData[2].status == true && adapter.symtomsData[3].status == true && (adapter.symtomsData[0].status == false && adapter.symtomsData[4].status == false && adapter.symtomsData[5].status == false && adapter.symtomsData[6].status == false && adapter.symtomsData[7].status == false && adapter.symtomsData[8].status == false && adapter.symtomsData[9].status == false && adapter.symtomsData[10].status == false && adapter.symtomsData[11].status == false && adapter.symtomsData[12].status == false && adapter.symtomsData[13].status == false && adapter.symtomsData[14].status == false)) {
                historyId = UUID.randomUUID().toString()
                getDataDisease("p1", historyId, date)
            } else if (adapter.symtomsData[4].status == true && adapter.symtomsData[5].status == true && adapter.symtomsData[0].status == false && adapter.symtomsData[1].status == false && adapter.symtomsData[2].status == false && adapter.symtomsData[3].status == false && adapter.symtomsData[6].status == false && adapter.symtomsData[7].status == false && adapter.symtomsData[8].status == false && adapter.symtomsData[9].status == false && adapter.symtomsData[10].status == false && adapter.symtomsData[11].status == false && adapter.symtomsData[12].status == false && adapter.symtomsData[13].status == false && adapter.symtomsData[14].status == false) {
                historyId = UUID.randomUUID().toString()
                getDataDisease("p2", historyId, date)
            } else if (adapter.symtomsData[1].status == true && adapter.symtomsData[6].status == true && adapter.symtomsData[7].status == true && adapter.symtomsData[0].status == false && adapter.symtomsData[2].status == false && adapter.symtomsData[3].status == false && adapter.symtomsData[4].status == false && adapter.symtomsData[5].status == false && adapter.symtomsData[8].status == false && adapter.symtomsData[9].status == false && adapter.symtomsData[10].status == false && adapter.symtomsData[11].status == false && adapter.symtomsData[12].status == false && adapter.symtomsData[13].status == false && adapter.symtomsData[14].status == false) {
                historyId = UUID.randomUUID().toString()
                getDataDisease("p3", historyId, date)
            } else if (adapter.symtomsData[8].status == true && adapter.symtomsData[9].status == true && adapter.symtomsData[10].status == true && adapter.symtomsData[0].status == false && adapter.symtomsData[1].status == false && adapter.symtomsData[2].status == false && adapter.symtomsData[3].status == false && adapter.symtomsData[4].status == false && adapter.symtomsData[5].status == false && adapter.symtomsData[6].status == false && adapter.symtomsData[7].status == false && adapter.symtomsData[11].status == false && adapter.symtomsData[12].status == false && adapter.symtomsData[13].status == false && adapter.symtomsData[14].status == false) {
                historyId = UUID.randomUUID().toString()
                getDataDisease("p4", historyId, date)
            } else if (adapter.symtomsData[4].status == true && adapter.symtomsData[11].status == true && adapter.symtomsData[12].status == true && adapter.symtomsData[0].status == false && adapter.symtomsData[1].status == false && adapter.symtomsData[2].status == false && adapter.symtomsData[3].status == false && adapter.symtomsData[5].status == false && adapter.symtomsData[6].status == false && adapter.symtomsData[7].status == false && adapter.symtomsData[8].status == false && adapter.symtomsData[9].status == false && adapter.symtomsData[10].status == false && adapter.symtomsData[13].status == false && adapter.symtomsData[14].status == false) {
                historyId = UUID.randomUUID().toString()
                getDataDisease("p5", historyId, date)
            } else if (adapter.symtomsData[4].status == true || adapter.symtomsData[9].status == true || adapter.symtomsData[11].status == true && adapter.symtomsData[13].status == true && adapter.symtomsData[0].status == false && adapter.symtomsData[1].status == false && adapter.symtomsData[2].status == false && adapter.symtomsData[3].status == false && adapter.symtomsData[5].status == false && adapter.symtomsData[6].status == false && adapter.symtomsData[7].status == false && adapter.symtomsData[8].status == false && adapter.symtomsData[10].status == false && adapter.symtomsData[12].status == false && adapter.symtomsData[14].status == false) {
                historyId = UUID.randomUUID().toString()
                getDataDisease("p6", historyId, date)
            } else if (adapter.symtomsData[0].status == true && adapter.symtomsData[14].status == true && adapter.symtomsData[1].status == false && adapter.symtomsData[2].status == false && adapter.symtomsData[3].status == false && adapter.symtomsData[4].status == false && adapter.symtomsData[5].status == false && adapter.symtomsData[6].status == false && adapter.symtomsData[7].status == false && adapter.symtomsData[8].status == false && adapter.symtomsData[9].status == false && adapter.symtomsData[10].status == false && adapter.symtomsData[11].status == false && adapter.symtomsData[12].status == false && adapter.symtomsData[13].status == false) {
                historyId = UUID.randomUUID().toString()
                getDataDisease("p7", historyId, date)
            } else {
//                Toast.makeText(this, "Disease not found", Toast.LENGTH_LONG).show()
//                dialogSymptoms.show()
                showDialog()
            }
        }

//        val btnCloseSymptomsDialog: ImageButton = dialogView.findViewById(R.id.btnCloseSymptomsDialog)
//
//        btnCloseSymptomsDialog.setOnClickListener {
//            dialogSymptoms.dismiss()
//        }

        btnBackDiagnosis.setOnClickListener {
            finish()
        }

    }

    private fun showDialog() {
        dialogBinding = DialogSymptomsResultBinding.inflate(layoutInflater)
        dialog = Dialog(this, R.style.CustomAlertDialog)
        dialog.setContentView(dialogBinding.root)
        dialog.show()

        dialogBinding.btnCloseSymptomsDialog.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun getDataDisease(id: String, historyId: String, date: String) {
        database.collection("disease").document(id).get()
            .addOnSuccessListener { documents ->
                if (documents != null) {
                    disease = Disease(
                        id = documents.getString("id"),
                        image = documents.getString("image"),
                        name = documents.getString("name"),
                        solution = documents.getString("solution")
                    )
                    Log.d("GET DATA DISEASE", "Document exist")

                    history = History(
                        uid = auth.currentUser?.uid,
                        id = historyId,
                        name = disease.name,
                        image = disease.image,
                        solution = disease.solution,
                        date = date
                    )

                    //Add diagnosis result into history repository
                    addHistory(history)

                    val intent = Intent(this, ResultActivity::class.java)
                    intent.putExtra("disease", disease)
                    startActivity(intent)

                } else {
                    Log.e("GET DATA DISEASE", "No such document")
                }
            }
            .addOnFailureListener { e ->
                Log.e("GET DATA DISEASE", "Get data failed", e)
            }
    }

    private fun addHistory(history: History) {

        database.collection("history").document(history.id.toString())
            .set(history)
            .addOnSuccessListener {
                Log.d("ADD DATA HISTORY", "Add data history success")
            }
            .addOnFailureListener { e ->
                Log.e("ADD DATA HISTORY", "Add data history failed | ", e)
            }
    }

    override fun onItemSelected(position: Int, selection: Int) {
        adapter.symtomsData[position].answer = selection

        adapter.notifyItemChanged(position)
    }
}