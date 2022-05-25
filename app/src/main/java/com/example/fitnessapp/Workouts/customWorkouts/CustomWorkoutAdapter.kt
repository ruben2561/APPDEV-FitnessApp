package com.example.fitnessapp.Workouts.customWorkouts


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.R
import com.example.fitnessapp.Workouts.Workout

class CustomWorkoutAdapter(private var items: List<Workout>, private var onItemClickListener: OnItemClickListener):
    RecyclerView.Adapter<CustomWorkoutAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.custom_workout, parent, false)
        return ViewHolder(view, onItemClickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val workout = items[position]
        holder.bind(workout)
        holder.workout = workout
    }

    class ViewHolder(itemView: View, onItemClickListener: OnItemClickListener): RecyclerView.ViewHolder(itemView) {
        lateinit var workout: Workout
        init {
            itemView.setOnClickListener {
                onItemClickListener.OnClick(adapterPosition)
            }

            itemView.setOnLongClickListener {
                onItemClickListener.OnLongClick(adapterPosition)
                return@setOnLongClickListener true
            }
            itemView.apply { findViewById<ImageButton>(R.id.deleteButton).setOnClickListener{
                onItemClickListener.DeleteWorkout(workout)
            }}
        }

        fun bind(workout: Workout) {
            itemView.apply { findViewById<TextView>(R.id.txtWorkoutName).text = workout.name }
            itemView.apply {
                findViewById<TextView>(R.id.txtExercises).text =
                    "Excercises id's: " + workout.exersicesId
            }
            itemView.apply {
                findViewById<TextView>(R.id.txtNumberOfExercises).text =
                    "Number of excercises: " + ((workout.exersicesId.length / 2) - 2)
            }
        }
    }

    interface OnItemClickListener{
        fun OnClick(position: Int)
        fun OnLongClick(position: Int)
        fun DeleteWorkout(workout: Workout)
    }
}