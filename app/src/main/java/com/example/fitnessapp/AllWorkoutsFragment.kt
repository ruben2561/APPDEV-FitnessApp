package com.example.fitnessapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.fitnessapp.database.Excercise
import com.example.fitnessapp.databaseWorkouts.Workout
import com.example.fitnessapp.databaseWorkouts.WorkoutAdapter
import com.example.fitnessapp.databaseWorkouts.WorkoutDao
import com.example.fitnessapp.databaseWorkouts.WorkoutDatabase
import com.example.fitnessapp.databinding.FragmentAllWorkoutsBinding

class AllWorkoutsFragment : Fragment() {

    private lateinit var binding: FragmentAllWorkoutsBinding
    lateinit var db: WorkoutDatabase
    lateinit var workoutDao: WorkoutDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllWorkoutsBinding.inflate(layoutInflater)
        db = Room.databaseBuilder(requireContext(), WorkoutDatabase::class.java, "workouts database").allowMainThreadQueries().build()  //
        workoutDao = db.workoutDao()
        val workout: MutableList<Workout> = workoutDao.getAll()                                                                              // gets all database items and puts it in a list

        /*workoutDao.insert(listOf(Workout("schema 1", "20,50,88,100", 0),
                                 Workout("schema 2", "101,50,88,14,55", 0)))*/

        binding.rvwWorkouts.adapter = WorkoutAdapter(workout)                                                   // adds the excercises list in the recyclerview
        binding.rvwWorkouts.layoutManager = LinearLayoutManager(context)                                        // chooses what type of layout
        binding.rvwWorkouts.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))   // this puts a line between every item

        return binding.root
    }

    fun addWorkout(workout: Workout){
        if(workoutDao != null){
            workoutDao.insertOne(workout)
        }
    }



}