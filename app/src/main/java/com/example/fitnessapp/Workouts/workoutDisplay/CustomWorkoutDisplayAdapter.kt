package com.example.fitnessapp.Workouts.workoutDisplay

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.R
import com.example.fitnessapp.Workouts.newWorkout.CustomExercise

class CustomWorkoutDisplayAdapter(private var items: List<CustomExercise>):
    RecyclerView.Adapter<CustomWorkoutDisplayAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.display_exercise, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = items[position]

        val input: String = exercise.repsAndWeight
        val result = input.split(",").map { it.trim() }

        holder.result = result
        holder.bind(exercise)


    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        lateinit var result: List<String>

        fun bind(exercise: CustomExercise) {
            itemView.apply { findViewById<TextView>(R.id.txtExerciseName).text = exercise.name }
            itemView.apply { findViewById<TextView>(R.id.txtMuscleGroup).text = "Muscle Group: " + exercise.muscleGroup }

            itemView.apply { findViewById<TextView>(R.id.repsDisplay1).text = result[0] }
            itemView.apply { findViewById<TextView>(R.id.repsDisplay2).text = result[2] }
            itemView.apply { findViewById<TextView>(R.id.repsDisplay3).text = result[4] }

            itemView.apply { findViewById<TextView>(R.id.weightDisplay1).text = result[1] }
            itemView.apply { findViewById<TextView>(R.id.weightDisplay2).text = result[3] }
            itemView.apply { findViewById<TextView>(R.id.weightDisplay3).text = result[5] }
        }
    }
}