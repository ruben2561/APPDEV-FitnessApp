package com.example.fitnessapp.Workouts.workoutDisplay

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.R
import com.example.fitnessapp.Workouts.newWorkout.CustomExercise
import java.util.*

class DefaultWorkoutDisplayAdapter(private var items: List<CustomExercise>, private var onItemClickListener: OnItemClickListener):
    RecyclerView.Adapter<DefaultWorkoutDisplayAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.custom_exercise, parent, false)
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
            itemView.apply { findViewById<EditText>(R.id.reps1).addTextChangedListener(object :
                TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {var listRepsAndWeight = Arrays.asList("","","","","","")

                    itemView.apply{listRepsAndWeight[0] = findViewById<TextView>(R.id.reps1).text.toString()}
                    itemView.apply{listRepsAndWeight[1] = findViewById<TextView>(R.id.weight1).text.toString()}
                    itemView.apply{listRepsAndWeight[2] = findViewById<TextView>(R.id.reps2).text.toString()}
                    itemView.apply{listRepsAndWeight[3] = findViewById<TextView>(R.id.weight2).text.toString()}
                    itemView.apply{listRepsAndWeight[4] = findViewById<TextView>(R.id.reps3).text.toString()}
                    itemView.apply{listRepsAndWeight[5] = findViewById<TextView>(R.id.weight3).text.toString()}

                    val separatedSerialNumber = listRepsAndWeight.joinToString (separator = ",") {it}
                    custom.repsAndWeight = separatedSerialNumber }
            })}
            itemView.apply { findViewById<EditText>(R.id.weight1).addTextChangedListener(object :
                TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {var listRepsAndWeight = Arrays.asList("","","","","","")

                    itemView.apply{listRepsAndWeight[0] = findViewById<TextView>(R.id.reps1).text.toString()}
                    itemView.apply{listRepsAndWeight[1] = findViewById<TextView>(R.id.weight1).text.toString()}
                    itemView.apply{listRepsAndWeight[2] = findViewById<TextView>(R.id.reps2).text.toString()}
                    itemView.apply{listRepsAndWeight[3] = findViewById<TextView>(R.id.weight2).text.toString()}
                    itemView.apply{listRepsAndWeight[4] = findViewById<TextView>(R.id.reps3).text.toString()}
                    itemView.apply{listRepsAndWeight[5] = findViewById<TextView>(R.id.weight3).text.toString()}

                    val separatedSerialNumber = listRepsAndWeight.joinToString (separator = ",") {it}
                    custom.repsAndWeight = separatedSerialNumber}
            })}
            itemView.apply { findViewById<EditText>(R.id.reps2).addTextChangedListener(object :
                TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {var listRepsAndWeight = Arrays.asList("","","","","","")

                    itemView.apply{listRepsAndWeight[0] = findViewById<TextView>(R.id.reps1).text.toString()}
                    itemView.apply{listRepsAndWeight[1] = findViewById<TextView>(R.id.weight1).text.toString()}
                    itemView.apply{listRepsAndWeight[2] = findViewById<TextView>(R.id.reps2).text.toString()}
                    itemView.apply{listRepsAndWeight[3] = findViewById<TextView>(R.id.weight2).text.toString()}
                    itemView.apply{listRepsAndWeight[4] = findViewById<TextView>(R.id.reps3).text.toString()}
                    itemView.apply{listRepsAndWeight[5] = findViewById<TextView>(R.id.weight3).text.toString()}

                    val separatedSerialNumber = listRepsAndWeight.joinToString (separator = ",") {it}
                    custom.repsAndWeight = separatedSerialNumber}
            })}
            itemView.apply { findViewById<EditText>(R.id.weight2).addTextChangedListener(object :
                TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {var listRepsAndWeight = Arrays.asList("","","","","","")

                    itemView.apply{listRepsAndWeight[0] = findViewById<TextView>(R.id.reps1).text.toString()}
                    itemView.apply{listRepsAndWeight[1] = findViewById<TextView>(R.id.weight1).text.toString()}
                    itemView.apply{listRepsAndWeight[2] = findViewById<TextView>(R.id.reps2).text.toString()}
                    itemView.apply{listRepsAndWeight[3] = findViewById<TextView>(R.id.weight2).text.toString()}
                    itemView.apply{listRepsAndWeight[4] = findViewById<TextView>(R.id.reps3).text.toString()}
                    itemView.apply{listRepsAndWeight[5] = findViewById<TextView>(R.id.weight3).text.toString()}

                    val separatedSerialNumber = listRepsAndWeight.joinToString (separator = ",") {it}
                    custom.repsAndWeight = separatedSerialNumber}
            })}
            itemView.apply { findViewById<EditText>(R.id.reps3).addTextChangedListener(object :
                TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {var listRepsAndWeight = Arrays.asList("","","","","","")

                    itemView.apply{listRepsAndWeight[0] = findViewById<TextView>(R.id.reps1).text.toString()}
                    itemView.apply{listRepsAndWeight[1] = findViewById<TextView>(R.id.weight1).text.toString()}
                    itemView.apply{listRepsAndWeight[2] = findViewById<TextView>(R.id.reps2).text.toString()}
                    itemView.apply{listRepsAndWeight[3] = findViewById<TextView>(R.id.weight2).text.toString()}
                    itemView.apply{listRepsAndWeight[4] = findViewById<TextView>(R.id.reps3).text.toString()}
                    itemView.apply{listRepsAndWeight[5] = findViewById<TextView>(R.id.weight3).text.toString()}

                    val separatedSerialNumber = listRepsAndWeight.joinToString (separator = ",") {it}
                    custom.repsAndWeight = separatedSerialNumber}
            })}
            itemView.apply { findViewById<EditText>(R.id.weight3).addTextChangedListener(object :
                TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {var listRepsAndWeight = Arrays.asList("","","","","","")

                    itemView.apply{listRepsAndWeight[0] = findViewById<TextView>(R.id.reps1).text.toString()}
                    itemView.apply{listRepsAndWeight[1] = findViewById<TextView>(R.id.weight1).text.toString()}
                    itemView.apply{listRepsAndWeight[2] = findViewById<TextView>(R.id.reps2).text.toString()}
                    itemView.apply{listRepsAndWeight[3] = findViewById<TextView>(R.id.weight2).text.toString()}
                    itemView.apply{listRepsAndWeight[4] = findViewById<TextView>(R.id.reps3).text.toString()}
                    itemView.apply{listRepsAndWeight[5] = findViewById<TextView>(R.id.weight3).text.toString()}

                    val separatedSerialNumber = listRepsAndWeight.joinToString (separator = ",") {it}
                    custom.repsAndWeight = separatedSerialNumber}
            })}

            itemView.apply { findViewById<ImageButton>(R.id.infoButton).setOnClickListener{
                onItemClickListener.exerciseInfo(custom)
            }}
        }

        fun bind(customExercise: CustomExercise){

            itemView.apply{findViewById<TextView>(R.id.txtExerciseName).text = customExercise.name}
            itemView.apply{findViewById<TextView>(R.id.txtMuscleGroup).text = customExercise.muscleGroup}

            itemView.apply { findViewById<TextView>(R.id.reps1).text = result[0] }
            itemView.apply { findViewById<TextView>(R.id.reps2).text = result[2] }
            itemView.apply { findViewById<TextView>(R.id.reps3).text = result[4] }

            itemView.apply { findViewById<TextView>(R.id.weight1).text = result[1] }
            itemView.apply { findViewById<TextView>(R.id.weight2).text = result[3] }
            itemView.apply { findViewById<TextView>(R.id.weight3).text = result[5] }
        }
    }

    interface OnItemClickListener{
        fun exerciseInfo(custom: CustomExercise)
    }

}