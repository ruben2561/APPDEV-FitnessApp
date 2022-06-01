package com.example.fitnessapp.Workouts.allWorkouts


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.R

class AllWorkoutsAdapter(private var items: List<DefaultWorkout>, private var onItemClickListener: OnItemClickListener):
    RecyclerView.Adapter<AllWorkoutsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.default_workout, parent, false)
        return ViewHolder(view, onItemClickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val defaultWorkout = items[position]
        holder.bind(defaultWorkout)
        holder.defaultWorkout = defaultWorkout
    }

    class ViewHolder(itemView: View, onItemClickListener: OnItemClickListener): RecyclerView.ViewHolder(itemView) {
        lateinit var defaultWorkout: DefaultWorkout
        init {
            itemView.apply { findViewById<ImageButton>(R.id.shareButton).setOnClickListener{
                onItemClickListener.shareWorkout(defaultWorkout)
            }}
            itemView.apply { findViewById<ImageButton>(R.id.beginButton).setOnClickListener{
                onItemClickListener.beginWorkout(defaultWorkout)
            }}
        }

        fun bind(defaultWorkout: DefaultWorkout) {
            itemView.apply { findViewById<TextView>(R.id.txtWorkoutName).text = defaultWorkout.name }
            itemView.apply {
                val input: String = defaultWorkout.exersicesId
                val result = input.split(",").map { it.trim() }
                findViewById<TextView>(R.id.txtNumberOfExercises).text =
                    "Exercises: " + result.size
            }
        }
    }



    interface OnItemClickListener{
        fun shareWorkout(defaultWorkout: DefaultWorkout)
        fun beginWorkout(defaultWorkout: DefaultWorkout)
    }
}