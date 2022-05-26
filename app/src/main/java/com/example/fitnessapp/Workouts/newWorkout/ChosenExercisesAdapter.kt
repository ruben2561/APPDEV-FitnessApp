package com.example.fitnessapp.Workouts.newWorkout


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.R
import com.example.fitnessapp.exercises.Exercise


class ChosenExercisesAdapter(private var items: List<CustomExercise>, private var onItemClickListener: OnItemClickListener):
    RecyclerView.Adapter<ChosenExercisesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.custom_exercise, parent, false)
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
                onItemClickListener.OnClick(adapterPosition)
            }
            itemView.setOnLongClickListener{
                onItemClickListener.OnLongClick(adapterPosition)
                return@setOnLongClickListener true
            }
        }

        fun bind(exercise: CustomExercise){
            itemView.apply{findViewById<TextView>(R.id.txtExerciseName).text = exercise.name}
            itemView.apply{findViewById<TextView>(R.id.txtMuscleGroup).text = exercise.muscleGroup}
            /*itemView.apply{exercise.repsAndWeight = findViewById<TextView>(R.id.reps1).text.toString()}
            itemView.apply{exercise.repsAndWeight = findViewById<TextView>(R.id.weight1).text.toString()}
            itemView.apply{exercise.repsAndWeight = findViewById<TextView>(R.id.reps2).text.toString()}
            itemView.apply{exercise.repsAndWeight = findViewById<TextView>(R.id.weight2).text.toString()}
            itemView.apply{exercise.repsAndWeight = findViewById<TextView>(R.id.reps3).text.toString()}
            itemView.apply{exercise.repsAndWeight = findViewById<TextView>(R.id.weight3).text.toString()}*/

        }

    }

    interface OnItemClickListener{
        fun OnClick(position: Int)
        fun OnLongClick(position: Int)
    }


}