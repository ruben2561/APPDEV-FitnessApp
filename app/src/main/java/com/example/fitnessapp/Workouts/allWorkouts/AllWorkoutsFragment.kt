package com.example.fitnessapp.Workouts.allWorkouts

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnessapp.GymBuddyDatabase
import com.example.fitnessapp.R
import com.example.fitnessapp.Workouts.workoutDisplay.WorkoutDisplayFragment
import com.example.fitnessapp.MainActivity
import com.example.fitnessapp.databinding.FragmentAllWorkoutsBinding

class AllWorkoutsFragment : Fragment(), AllWorkoutsAdapter.OnItemClickListener{

    private lateinit var binding: FragmentAllWorkoutsBinding
    lateinit var db: GymBuddyDatabase
    lateinit var defaultWorkoutDao: DefaultWorkoutDao
    lateinit var defaultWorkout: MutableList<DefaultWorkout>
    private lateinit var parentActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllWorkoutsBinding.inflate(layoutInflater)
        parentActivity = activity as MainActivity
        defaultWorkoutDao = parentActivity.db.defaultWorkoutDao()
        defaultWorkout = defaultWorkoutDao.getAll()                                                                              // gets all database items and puts it in a list


        binding.rvwWorkouts.adapter = AllWorkoutsAdapter(defaultWorkout, this)                                                   // adds the exercises list in the recyclerview
        binding.rvwWorkouts.layoutManager = LinearLayoutManager(context)                                        // chooses what type of layout
        binding.rvwWorkouts.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))   // this puts a line between every item

        return binding.root
    }

    override fun OnClick(ids: String, title: String) {
        val fragment: Fragment = WorkoutDisplayFragment(ids, title)
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val containerId = R.id.fragment_container
        fragmentTransaction.replace(containerId, fragment).addToBackStack(null).commit()
    }

    override fun OnLongClick(position: Int) {
        Toast.makeText(this.context, "long click on item: " + position, Toast.LENGTH_SHORT).show()
    }

}

/*defaultWorkoutDao.insert(listOf(
            DefaultWorkout("Biceps Workout", "10,208,200", 0),
            DefaultWorkout("Back Workout", "888,103,861,358", 0),
            DefaultWorkout("Triceps Workout", "236,152,851,198", 0),
            DefaultWorkout("Chest Workout", "369,883,590", 0),
            DefaultWorkout("Legs Workout", "74,474,73,162,579", 0),
            DefaultWorkout("Shoulders Workout", "270,960,150,293", 0),
            DefaultWorkout("Abs Workout", "614,216,640,564", 0),
            DefaultWorkout("Forearms Workout", "808,895", 0)))
            */