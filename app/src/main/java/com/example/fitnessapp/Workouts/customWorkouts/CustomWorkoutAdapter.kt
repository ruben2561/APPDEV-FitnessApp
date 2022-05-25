package com.example.fitnessapp.Workouts.customWorkouts


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.R

class CustomWorkoutAdapter(private var items: List<CustomWorkout>, private var onItemClickListener: OnItemClickListener):
    RecyclerView.Adapter<CustomWorkoutAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.custom_workout, parent, false)
        return ViewHolder(view, onItemClickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val customWorkout = items[position]
        holder.bind(customWorkout)
        holder.customWorkout = customWorkout
    }

    class ViewHolder(itemView: View, onItemClickListener: OnItemClickListener): RecyclerView.ViewHolder(itemView) {
        lateinit var customWorkout: CustomWorkout
        init {
            itemView.setOnClickListener {
                onItemClickListener.OnClick(customWorkout.exersicesId, customWorkout.name)
            }

            itemView.setOnLongClickListener {
                onItemClickListener.OnLongClick(adapterPosition)
                return@setOnLongClickListener true
            }
            itemView.apply { findViewById<ImageButton>(R.id.deleteButton).setOnClickListener{
                onItemClickListener.DeleteWorkout(customWorkout)
            }}
            itemView.apply { findViewById<ImageButton>(R.id.editButton).setOnClickListener{
                onItemClickListener.EditWorkout(customWorkout)
            }}
        }

        fun bind(customWorkout: CustomWorkout) {
            itemView.apply { findViewById<TextView>(R.id.txtWorkoutName).text = customWorkout.name }
            itemView.apply {
                val input: String = customWorkout.exersicesId
                var result = input.split(",").map { it.trim() }
                findViewById<TextView>(R.id.txtNumberOfExercises).text =
                    "Excercises: " + result.size
            }
        }
    }

    interface OnItemClickListener{
        fun OnClick(ids: String, title: String)
        fun OnLongClick(position: Int)
        fun DeleteWorkout(customWorkout: CustomWorkout)
        fun EditWorkout(customWorkout: CustomWorkout)
    }
}