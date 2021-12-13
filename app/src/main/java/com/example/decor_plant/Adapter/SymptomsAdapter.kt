package com.example.decor_plant.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.decor_plant.Model.Symptoms
import com.example.decor_plant.databinding.ItemSymtomsQuestionBinding

class SymptomsAdapter (private val interaction: Interaction) : RecyclerView.Adapter<SymptomsAdapter.ViewHolder>() {

    var symtomsData : ArrayList<Symptoms> = ArrayList()

    class ViewHolder(val binding : ItemSymtomsQuestionBinding, val interaction: Interaction) : RecyclerView.ViewHolder(binding.root){
        fun bind(symtoms: Symptoms, symtomsData : ArrayList<Symptoms>, position: Int){
            binding.apply {
                tvSymtoms.text = symtoms.symtom

                setRadios(symtoms.answer)

                if (interaction!=null){
                    rbYesSymtoms.setOnClickListener {
                        interaction.onItemSelected(position, 1)
                        symtomsData[position].status = true
                    }
                    rbNoSymptoms.setOnClickListener {
                        interaction.onItemSelected(position, 2)
                        symtomsData[position].status = false
                    }
                }
            }
        }

        fun setRadios(answer: Int?) {
            //bug fix: clear RadioGroup selection before setting the values
            // otherwise checked answers sometimes disappear on scroll
            binding.apply {
                rgSymtoms.clearCheck()

                if (answer == 0) return //skip setting checked if no answer is selected

                when (answer) {
                    1 -> rbYesSymtoms.isChecked = true
                    2 -> rbNoSymptoms.isChecked = true
                }
            }
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SymptomsAdapter.ViewHolder =
        ViewHolder(
            ItemSymtomsQuestionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            interaction
        )

    override fun onBindViewHolder(holder: SymptomsAdapter.ViewHolder, position: Int) {
        holder.bind(symtomsData[position], symtomsData, position)
    }

    override fun getItemCount(): Int = symtomsData.size

    interface Interaction{
        fun onItemSelected(position: Int, selection : Int)
    }
}
