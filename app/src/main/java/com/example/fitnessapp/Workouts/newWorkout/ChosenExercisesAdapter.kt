package com.example.fitnessapp.Workouts.newWorkout


import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.R
import com.example.fitnessapp.exercises.Exercise
import com.example.fitnessapp.exercises.ExerciseAdapter
import java.util.*


class ChosenExercisesAdapter(private var items: List<CustomExercise>, private var onItemClickListener: OnItemClickListener):
    RecyclerView.Adapter<ChosenExercisesAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.default_exercise, parent, false)
        return ViewHolder(view, onItemClickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = items[position]

        var input: String = exercise.repsAndWeight
        var result = input.split(",").map { it.trim() }
        holder.result = result
        holder.custom = exercise
        holder.bind(exercise)



    }

    class ViewHolder(itemView: View, onItemClickListener: OnItemClickListener): RecyclerView.ViewHolder(itemView){
        lateinit var custom: CustomExercise
        lateinit var result: List<String>

        init{

            itemView.setOnLongClickListener{
                onItemClickListener.onLongClick(adapterPosition)
                return@setOnLongClickListener true
            }
        }

        fun bind(customExercise: CustomExercise){


            itemView.apply{findViewById<TextView>(R.id.txtExerciseName).text = customExercise.name}
            itemView.apply{findViewById<TextView>(R.id.txtMuscleGroup).text = customExercise.muscleGroup}

            /*itemView.apply { findViewById<TextView>(R.id.reps1).text = result[0] }
            itemView.apply { findViewById<TextView>(R.id.reps2).text = result[2] }
            itemView.apply { findViewById<TextView>(R.id.reps3).text = result[4] }

            itemView.apply { findViewById<TextView>(R.id.weight1).text = result[1] }
            itemView.apply { findViewById<TextView>(R.id.weight2).text = result[3] }
            itemView.apply { findViewById<TextView>(R.id.weight3).text = result[5] }*/
        }

    }

    interface OnItemClickListener{
        fun onLongClick(position: Int)
        fun addWeights(position: Int)
    }

}