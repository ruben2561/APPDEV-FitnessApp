package com.example.fitnessapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.core.view.contains
import androidx.core.view.get
import androidx.core.view.iterator
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnessapp.database.Excercise
import com.example.fitnessapp.database.ExcerciseAdapter
import com.example.fitnessapp.databaseWorkouts.Workout
import com.example.fitnessapp.databinding.FragmentNewWorkoutBinding

class NewWorkoutFragment : Fragment() {

    private lateinit var binding: FragmentNewWorkoutBinding
    private lateinit var allWorkoutsFragment: AllWorkoutsFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewWorkoutBinding.inflate(layoutInflater)
        val choisesList: ArrayList<Excercise> = ArrayList()
        binding.rvwExcercises.adapter = ExcerciseAdapter(choisesList)
        binding.rvwExcercises.layoutManager = LinearLayoutManager(context)

        binding.saveWorkout.setOnClickListener{
            var name = binding.workoutTitle.text.toString()
            var excercisesIds = ""

            for (item: View in binding.rvwExcercises){
                excercisesIds = excercisesIds + binding.rvwExcercises.id + ","
                //excercisesIds.plus(binding.rvwExcercises.id) + ","
            }

            allWorkoutsFragment.addWorkout(Workout(name, excercisesIds, 0))
        }


        return binding.root
    }
}