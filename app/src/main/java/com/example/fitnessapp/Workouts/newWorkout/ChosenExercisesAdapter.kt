package com.example.fitnessapp.Workouts.newWorkout


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.R
import com.example.fitnessapp.exercises.Exercise
import java.util.*


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
                onItemClickListener.onClick(adapterPosition)
            }
            itemView.setOnLongClickListener{
                onItemClickListener.onLongClick(adapterPosition)
                return@setOnLongClickListener true
            }
        }

        fun bind(customExercise: CustomExercise){
            var listRepsAndWeight = Arrays.asList("","","","","","")

            itemView.apply{findViewById<TextView>(R.id.txtExerciseName).text = customExercise.name}
            itemView.apply{findViewById<TextView>(R.id.txtMuscleGroup).text = customExercise.muscleGroup}

            itemView.apply{listRepsAndWeight[0] = findViewById<TextView>(R.id.reps1).text.toString()}
            itemView.apply{listRepsAndWeight[1] = findViewById<TextView>(R.id.weight1).text.toString()}
            itemView.apply{listRepsAndWeight[2] = findViewById<TextView>(R.id.reps2).text.toString()}
            itemView.apply{listRepsAndWeight[3] = findViewById<TextView>(R.id.weight2).text.toString()}
            itemView.apply{listRepsAndWeight[4] = findViewById<TextView>(R.id.reps3).text.toString()}
            itemView.apply{listRepsAndWeight[5] = findViewById<TextView>(R.id.weight3).text.toString()}

            val separatedSerialNumber = listRepsAndWeight.joinToString (separator = ",") {it}
            customExercise.repsAndWeight = separatedSerialNumber
        }

    }

    interface OnItemClickListener{
        fun onClick(position: Int)
        fun onLongClick(position: Int)
    }


}