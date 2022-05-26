package com.example.fitnessapp.exercises


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.R


class ExerciseAdapter(val items: List<Exercise>, private var onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.default_exercise, parent, false)
        return ExerciseViewHolder(view, onItemClickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val currentExerciseItem = items[position]
        holder.bind(currentExerciseItem)
        holder.currentExercise = currentExerciseItem
    }

    class ExerciseViewHolder(currentItemView: View, onItemClickListener: OnItemClickListener) : RecyclerView.ViewHolder(currentItemView){
        lateinit var currentExercise: Exercise
        init{
            currentItemView.setOnClickListener{
                onItemClickListener.OnClick(currentExercise)
            }
        }

        fun bind(currentExerciseItem: Exercise){
            itemView.apply {
                findViewById<TextView>(R.id.txtExerciseName).text = currentExerciseItem.name
                findViewById<TextView>(R.id.txtMuscleGroup).text = "Muscle Group: " + currentExerciseItem.muscleGroup
            }
        }
    }

    interface OnItemClickListener{
        fun OnClick(exercise: Exercise)
    }
}
