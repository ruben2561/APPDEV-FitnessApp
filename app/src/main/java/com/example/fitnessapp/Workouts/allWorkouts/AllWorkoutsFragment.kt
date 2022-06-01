package com.example.fitnessapp.Workouts.allWorkouts

import android.content.Intent
import android.os.Bundle
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
import com.example.fitnessapp.Workouts.customWorkouts.CustomWorkout
import com.example.fitnessapp.Workouts.customWorkouts.CustomWorkoutAdapter
import com.example.fitnessapp.Workouts.customWorkouts.CustomWorkoutDao
import com.example.fitnessapp.Workouts.newWorkout.NewWorkoutFragment
import com.example.fitnessapp.Workouts.workoutDisplay.CustomWorkoutDisplayFragment
import com.example.fitnessapp.Workouts.workoutDisplay.DefaultWorkoutDisplayFragment
import com.example.fitnessapp.databinding.FragmentAllWorkoutsBinding
import com.example.fitnessapp.databinding.FragmentCustomWorkoutsBinding
import com.example.fitnessapp.exercises.Exercise
import com.google.android.material.snackbar.Snackbar

class AllWorkoutsFragment : Fragment(), AllWorkoutsAdapter.OnItemClickListener{

    private lateinit var binding: FragmentAllWorkoutsBinding
    private lateinit var defaultWorkoutDao: DefaultWorkoutDao
    private lateinit var defaultWorkout: MutableList<DefaultWorkout>
    private lateinit var parentActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllWorkoutsBinding.inflate(layoutInflater)
        parentActivity = activity as MainActivity
        defaultWorkoutDao = parentActivity.db.defaultWorkoutDao()



        defaultWorkout = defaultWorkoutDao.getAll()                                                                              // gets all database items and puts it in a list

        binding.rvwWorkouts.adapter = AllWorkoutsAdapter(defaultWorkout, this)                                                   // adds the exercises list in the recyclerview
        binding.rvwWorkouts.layoutManager = LinearLayoutManager(context)                                        // chooses what type of layout
        binding.rvwWorkouts.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))   // this puts a line between every item

        return binding.root
    }

    override fun shareWorkout(defaultWorkout: DefaultWorkout){
        val input: String = defaultWorkout.exersicesId
        val result = input.split(",").map { it.trim() }
        val resultInt = result.map { it.toInt() }.toIntArray()

        val input2: String = defaultWorkout.repsAndWeight
        val result2 = input2.split(",").map { it.trim() }
        var teller = 0

        val exerciseDao = parentActivity.db.exerciseDao()                                                                                                               //

        val exercises: List<Exercise> = exerciseDao.loadAllByIds(resultInt)

        var stringToSend: String = defaultWorkout.name + "\n" + "----------------------" + "\n"

        for(item in exercises){
            stringToSend = stringToSend + item.name + "\n"

            var j = 0
            while(j < 5){
                stringToSend = stringToSend + result2[j+teller] + "x   |   " //reps
                stringToSend = stringToSend + result2[j+teller+1] + "Kg(s)"  // kgs
                stringToSend = stringToSend + "\n"
                j = j+2
            }
            stringToSend = stringToSend + "\n"
            teller = teller + 6
        }


        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, stringToSend)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    override fun beginWorkout(defaultWorkout: DefaultWorkout) {
        val fragment: Fragment = DefaultWorkoutDisplayFragment(defaultWorkout)
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val containerId = R.id.fragment_container
        fragmentTransaction.replace(containerId, fragment).addToBackStack(null).commit()
    }

}

/*defaultWorkoutDao.insert(listOf(
            DefaultWorkout("Biceps Workout - by Joe Wicks", "10,208,200", "12, ,12, ,12, ,12, ,12, ,12, ,12, ,12, ,12, ,", 0),
            DefaultWorkout("Back Workout - by Scott Laidler", "888,103,861,358", "12, ,12, ,12, ,12, ,12, ,12, ,12, ,12, ,12, ,12, ,12, ,12, ,", 0),
            DefaultWorkout("Triceps Workout - by Nick Mitchell", "236,152,851,198", "12, ,12, ,12, ,12, ,12, ,12, ,12, ,12, ,12, ,12, ,12, ,12, ,", 0),
            DefaultWorkout("Chest Workout - by Kayla Itsines", "369,883,590", "12, ,12, ,12, ,12, ,12, ,12, ,12, ,12, ,12, ,", 0),
            DefaultWorkout("Legs Workout - by Adrian Collins", "74,474,73,162,579", "12, ,12, ,12, ,12, ,12, ,12, ,12, ,12, ,12, ,12, ,12, ,12, ,12, ,12, ,12, ,", 0),
            DefaultWorkout("Shoulders Workout - by Jeremy Ethier", "270,960,150,293", "12, ,12, ,12, ,12, ,12, ,12, ,12, ,12, ,12, ,12, ,12, ,12, ,", 0),
            DefaultWorkout("Abs Workout - by Tristyn Lee", "614,216,640,564", "12, ,12, ,12, ,12, ,12, ,12, ,12, ,12, ,12, ,12, ,12, ,12, ,", 0),
            DefaultWorkout("Forearms Workout - by Chris Heria", "808,895", "12, ,12, ,12, ,12, ,12, ,12, ,", 0)))
            */