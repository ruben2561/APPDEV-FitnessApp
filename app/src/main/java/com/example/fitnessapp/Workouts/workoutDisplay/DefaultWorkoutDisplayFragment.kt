package com.example.fitnessapp.Workouts.workoutDisplay

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnessapp.MainActivity
import com.example.fitnessapp.R
import com.example.fitnessapp.Workouts.allWorkouts.AllWorkoutsFragment
import com.example.fitnessapp.Workouts.allWorkouts.DefaultWorkout
import com.example.fitnessapp.Workouts.allWorkouts.DefaultWorkoutDao
import com.example.fitnessapp.Workouts.customWorkouts.CustomWorkout
import com.example.fitnessapp.Workouts.customWorkouts.CustomWorkoutDao
import com.example.fitnessapp.Workouts.customWorkouts.CustomWorkoutsFragment
import com.example.fitnessapp.Workouts.newWorkout.CustomExercise
import com.example.fitnessapp.databinding.FragmentWorkoutDisplayBinding
import com.example.fitnessapp.exercises.Exercise
import com.example.fitnessapp.exercises.ExerciseDao
import com.example.fitnessapp.exercises.ExercisesDetailsFragment
import java.util.*
import kotlin.collections.ArrayList

class DefaultWorkoutDisplayFragment(defaultWorkout: DefaultWorkout) : Fragment(), CustomWorkoutDisplayAdapter.OnItemClickListener{
    private lateinit var binding: FragmentWorkoutDisplayBinding
    private lateinit var exerciseDao: ExerciseDao
    private lateinit var defaultWorkoutDao: DefaultWorkoutDao
    private var ids = defaultWorkout.exersicesId
    private var defaultWorkout = defaultWorkout
    lateinit var parentActivity: MainActivity
    var choicesListCustom = ArrayList<CustomExercise>()

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

        val input2: String = defaultWorkout.repsAndWeight
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

        binding.txtWorkoutTitle.text = defaultWorkout.name
        binding.txtWorkoutQuantity.text = "Number of exercises: " + resultInt.size

        binding.rvExercises.adapter = CustomWorkoutDisplayAdapter(customExercises, this)                                                  // adds the exercises list in the recyclerview
        binding.rvExercises.layoutManager = LinearLayoutManager(context)                                                // chooses what type of layout
        binding.rvExercises.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))    // this puts a line between every item


        binding.endWorkout.setOnClickListener{
            parentActivity = activity as MainActivity
            defaultWorkoutDao = parentActivity.db.defaultWorkoutDao()
            val tempDate = DateFormat.format("dd-MM-yyyy", Date())

            var exercisesRepsAndWeight = ""
            for(item in customExercises){
                exercisesRepsAndWeight = exercisesRepsAndWeight + item.repsAndWeight + ","
            }

            defaultWorkoutDao.delete(defaultWorkout)

            defaultWorkoutDao.insertOne(
                DefaultWorkout(
                defaultWorkout.name,
                defaultWorkout.exersicesId,
                exercisesRepsAndWeight,
                0)
            )
            Toast.makeText(this.context, "Workout ended!", Toast.LENGTH_LONG).show()

            val fragment: Fragment = AllWorkoutsFragment()
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            val containerId = R.id.fragment_container
            fragmentTransaction.replace(containerId, fragment).addToBackStack(null).commit()
        }


        return binding.root
    }

    override fun exerciseInfo(custom: CustomExercise) {
        val fragment: Fragment = ExercisesDetailsFragment(Exercise(custom.name,custom.muscleGroup,0))
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val containerId = R.id.fragment_container
        fragmentTransaction.replace(containerId, fragment).addToBackStack(null).commit()
    }
}