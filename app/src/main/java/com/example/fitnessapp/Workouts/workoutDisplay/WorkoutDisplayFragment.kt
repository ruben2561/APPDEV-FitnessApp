package com.example.fitnessapp.Workouts.workoutDisplay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.fitnessapp.GymBuddyDatabase
import com.example.fitnessapp.databinding.FragmentWorkoutDisplayBinding
import com.example.fitnessapp.exercises.Exercise
import com.example.fitnessapp.exercises.ExerciseDao

class WorkoutDisplayFragment(exercisesIds: String, workoutTitle: String) : Fragment(){
    private lateinit var binding: FragmentWorkoutDisplayBinding
    lateinit var db: GymBuddyDatabase
    lateinit var exerciseDao: ExerciseDao
    var ids = exercisesIds
    var workoutTitle = workoutTitle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkoutDisplayBinding.inflate(layoutInflater)

        val input: String = ids
        var result = input.split(",").map { it.trim() }
        val resultInt = result.map { it.toInt() }.toIntArray()

        db = Room.databaseBuilder(requireContext(), GymBuddyDatabase::class.java, "gymBuddyDatabase.db").createFromAsset("databases/gymBuddyDatabase.db").allowMainThreadQueries().build() // .createFromAsset("databases/exercisedatabase-db.db")
        exerciseDao = db.exerciseDao()                                                                                                               //
        var exercises: List<Exercise> = exerciseDao.loadAllByIds(resultInt)                                                                              // gets all database items and puts it in a list

        binding.rvExercises.adapter = WorkoutDisplayAdapter(exercises)                                                  // adds the exercises list in the recyclerview
        binding.rvExercises.layoutManager = LinearLayoutManager(context)                                                // chooses what type of layout
        binding.rvExercises.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))    // this puts a line between every item

        binding.workoutTitle.text = workoutTitle
        binding.workoutQuantity.text = "Number of exercises: " + resultInt.size

        return binding.root
    }
}