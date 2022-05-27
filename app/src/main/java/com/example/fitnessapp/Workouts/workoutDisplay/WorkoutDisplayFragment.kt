package com.example.fitnessapp.Workouts.workoutDisplay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnessapp.MainActivity
import com.example.fitnessapp.Workouts.customWorkouts.CustomWorkout
import com.example.fitnessapp.Workouts.newWorkout.CustomExercise
import com.example.fitnessapp.databinding.FragmentWorkoutDisplayBinding
import com.example.fitnessapp.exercises.Exercise
import com.example.fitnessapp.exercises.ExerciseDao

class WorkoutDisplayFragment(customWorkout: CustomWorkout) : Fragment(){
    private lateinit var binding: FragmentWorkoutDisplayBinding
    private lateinit var exerciseDao: ExerciseDao
    private var ids = customWorkout.exersicesId
    private var customWorkout = customWorkout
    lateinit var parentActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkoutDisplayBinding.inflate(layoutInflater)
        parentActivity = activity as MainActivity
        val input: String = ids
        val result = input.split(",").map { it.trim() }
        val resultInt = result.map { it.toInt() }.toIntArray()
        exerciseDao = parentActivity.db.exerciseDao()                                                                                                               //
        val exercises: List<Exercise> = exerciseDao.loadAllByIds(resultInt)                                                                              // gets all database items and puts it in a list

        val input2: String = customWorkout.repsAndWeight
        val result2 = input2.split(",").map { it.trim() }

        var customExercises = ArrayList<CustomExercise>()
        var teller = 0
        for(item in exercises){
            var repsAndWeight = ""
            for (i in 0..5) {
                repsAndWeight = repsAndWeight + result2[i+teller] + ","
            }

            customExercises.add(CustomExercise(item.name,item.muscleGroup, repsAndWeight))

            teller = teller + 6
        }

        binding.rvExercises.adapter = WorkoutDisplayAdapter(customExercises)                                                  // adds the exercises list in the recyclerview
        binding.rvExercises.layoutManager = LinearLayoutManager(context)                                                // chooses what type of layout
        binding.rvExercises.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))    // this puts a line between every item

        binding.workoutTitle.text = customWorkout.name
        binding.workoutQuantity.text = "Number of exercises: " + resultInt.size

        return binding.root
    }
}