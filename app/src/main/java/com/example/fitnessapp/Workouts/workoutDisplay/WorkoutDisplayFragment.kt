package com.example.fitnessapp.Workouts.workoutDisplay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnessapp.MainActivity
import com.example.fitnessapp.databinding.FragmentWorkoutDisplayBinding
import com.example.fitnessapp.exercises.Exercise
import com.example.fitnessapp.exercises.ExerciseDao

class WorkoutDisplayFragment(exercisesIds: String, private val workoutTitle: String) : Fragment(){
    private lateinit var binding: FragmentWorkoutDisplayBinding
    private lateinit var exerciseDao: ExerciseDao
    private var ids = exercisesIds
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

        binding.rvExercises.adapter = WorkoutDisplayAdapter(exercises)                                                  // adds the exercises list in the recyclerview
        binding.rvExercises.layoutManager = LinearLayoutManager(context)                                                // chooses what type of layout
        binding.rvExercises.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))    // this puts a line between every item

        binding.workoutTitle.text = workoutTitle
        binding.workoutQuantity.text = "Number of exercises: " + resultInt.size

        return binding.root
    }
}