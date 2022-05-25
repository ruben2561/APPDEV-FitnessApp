package com.example.fitnessapp.Workouts.newWorkout


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.R
import com.example.fitnessapp.exercises.Exercise


class NewWorkoutAdapter(private var items: List<Exercise>, private var onItemClickListener: OnItemClickListener):
    RecyclerView.Adapter<NewWorkoutAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.exercise, parent, false)
        return ViewHolder(view, onItemClickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = items[position]
        holder.bind(exercise)
    }

    class ViewHolder(itemView: View, onItemClickListener: OnItemClickListener): RecyclerView.ViewHolder(itemView){

        init{
            itemView.setOnClickListener{
                //val snack: String = "item position clicked: $adapterPosition"
                //Snackbar.make(itemView, snack, Snackbar.LENGTH_SHORT).show()
                onItemClickListener.OnClick(adapterPosition)
            }
        }

        fun bind(exercise: Exercise){
            itemView.apply{findViewById<TextView>(R.id.txtExerciseName).text = exercise.name}
            itemView.apply{findViewById<TextView>(R.id.txtMuscleGroup).text = exercise.muscleGroup}
        }
    }

    interface OnItemClickListener{
        fun OnClick(position: Int)
    }


}
