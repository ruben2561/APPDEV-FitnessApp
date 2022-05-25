package com.example.fitnessapp.exercises


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.R


class ExerciseAdapter(val items: List<Exercise>) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    inner class ExerciseViewHolder(currentItemView: View) : RecyclerView.ViewHolder(currentItemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.exercise, parent, false)

        return ExerciseViewHolder(view)

    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val currentExerciseItem = items[position]
        holder.itemView.apply {
            findViewById<TextView>(R.id.txtExerciseName).text = currentExerciseItem.name
            findViewById<TextView>(R.id.txtMuscleGroup).text = "Muscle Group: " + currentExerciseItem.muscleGroup
        }
        /*holder.itemView.setOnClickListener(View.OnClickListener {
            strings.add(position)
            stringsLong = stringsLong + position + ","
            holder.itemView.apply {
                findViewById<TextView>(R.id.txtExerciseName).text = "ooo"
                findViewById<TextView>(R.id.txtExerciseName).setTextColor(Color.DKGRAY)
                findViewById<TextView>(R.id.txtMuscleGroup).setTextColor(Color.DKGRAY)

            }
        })*/



    }

    override fun getItemCount(): Int = items.size

}
