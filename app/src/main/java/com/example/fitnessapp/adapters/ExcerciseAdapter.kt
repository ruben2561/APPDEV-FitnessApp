package com.example.fitnessapp.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.R
import com.example.fitnessapp.databaseExcercises.Excercise


class ExcerciseAdapter(val items: List<Excercise>) : RecyclerView.Adapter<ExcerciseAdapter.ExcerciseViewHolder>() {

    inner class ExcerciseViewHolder(currentItemView: View) : RecyclerView.ViewHolder(currentItemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExcerciseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.excercise, parent, false)

        return ExcerciseViewHolder(view)

    }

    override fun onBindViewHolder(holder: ExcerciseViewHolder, position: Int) {
        val currentExcerciseItem = items[position]
        holder.itemView.apply {
            findViewById<TextView>(R.id.txtExcerciseName).text = currentExcerciseItem.name
            findViewById<TextView>(R.id.txtMuscleGroup).text = "Muscle Group: " + currentExcerciseItem.muscleGroup
        }
        /*holder.itemView.setOnClickListener(View.OnClickListener {
            strings.add(position)
            stringsLong = stringsLong + position + ","
            holder.itemView.apply {
                findViewById<TextView>(R.id.txtExcerciseName).text = "ooo"
                findViewById<TextView>(R.id.txtExcerciseName).setTextColor(Color.DKGRAY)
                findViewById<TextView>(R.id.txtMuscleGroup).setTextColor(Color.DKGRAY)

            }
        })*/



    }

    override fun getItemCount(): Int = items.size





}
