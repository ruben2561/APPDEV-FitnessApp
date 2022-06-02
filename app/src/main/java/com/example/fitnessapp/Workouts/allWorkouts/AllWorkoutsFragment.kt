package com.example.fitnessapp.Workouts.allWorkouts

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnessapp.MainActivity
import com.example.fitnessapp.R
import com.example.fitnessapp.Workouts.workoutDisplay.DefaultWorkoutDisplayFragment
import com.example.fitnessapp.databinding.FragmentAllWorkoutsBinding
import com.example.fitnessapp.exercises.Exercise

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

        defaultWorkout = defaultWorkoutDao.getAll()

        binding.rvwWorkouts.adapter = AllWorkoutsAdapter(defaultWorkout, this)
        binding.rvwWorkouts.layoutManager = LinearLayoutManager(context)
        binding.rvwWorkouts.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        return binding.root
    }

    override fun shareWorkout(defaultWorkout: DefaultWorkout){
        val input: String = defaultWorkout.exersicesId
        val result = input.split(",").map { it.trim() }
        val resultInt = result.map { it.toInt() }.toIntArray()

        val input2: String = defaultWorkout.repsAndWeight
        val result2 = input2.split(",").map { it.trim() }
        var teller = 0

        val exerciseDao = parentActivity.db.exerciseDao()
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


