package com.example.fitnessapp.Workouts.workoutDisplay

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.R
import com.example.fitnessapp.exercises.Exercise

class DefaultWorkoutDisplayAdapter(private var items: List<Exercise>):
    RecyclerView.Adapter<DefaultWorkoutDisplayAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.default_exercise, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = items[position]
        holder.bind(exercise)


    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(exercise: Exercise) {
            itemView.apply { findViewById<TextView>(R.id.txtExerciseName).text = exercise.name }
            itemView.apply { findViewById<TextView>(R.id.txtMuscleGroup).text = "Muscle Group: " + exercise.muscleGroup }
        }
    }
}