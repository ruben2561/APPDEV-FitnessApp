package com.example.fitnessapp.Workouts.customWorkouts

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
import androidx.room.Room
import com.example.fitnessapp.GymBuddyDatabase
import com.example.fitnessapp.R
import com.example.fitnessapp.Workouts.Workout
import com.example.fitnessapp.Workouts.CustomWorkoutDao
import com.example.fitnessapp.Workouts.newWorkout.NewWorkoutFragment
import com.example.fitnessapp.databinding.FragmentCustomWorkoutsBinding

class CustomWorkoutsFragment : Fragment(), CustomWorkoutAdapter.OnItemClickListener{

    private lateinit var binding: FragmentCustomWorkoutsBinding
    lateinit var db: GymBuddyDatabase
    lateinit var customWorkoutDao: CustomWorkoutDao
    lateinit var customWorkout: MutableList<Workout>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomWorkoutsBinding.inflate(layoutInflater)

        db = Room.databaseBuilder(requireContext(), GymBuddyDatabase::class.java, "gymBuddyDatabase").allowMainThreadQueries().build()  //
        customWorkoutDao = db.customWorkoutDao()
        customWorkout = customWorkoutDao.getAll()                                                                              // gets all database items and puts it in a list

        /*workoutDao.insert(listOf(Workout("schema 1", "20,50,88,100", 0),
                                 Workout("schema 2", "101,50,88,14,55", 0)))*/

        binding.rvwWorkouts.adapter = CustomWorkoutAdapter(customWorkout, this)                                                   // adds the excercises list in the recyclerview
        binding.rvwWorkouts.layoutManager = LinearLayoutManager(context)                                        // chooses what type of layout
        binding.rvwWorkouts.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))   // this puts a line between every item

        binding.newWorkoutButton.setOnClickListener{
            val fragment: Fragment = NewWorkoutFragment()
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            val containerId = R.id.fragment_container
            fragmentTransaction.replace(containerId, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return binding.root
    }

    override fun OnClick(position: Int) {
        Toast.makeText(this.context, "short click on item: " + position, Toast.LENGTH_SHORT).show()
    }

    override fun OnLongClick(position: Int) {
        //val intent = Intent(fragment, Activity3Recycler::class.java)
        //startActivity(intent)

        Toast.makeText(this.context, "long click on item: " + position, Toast.LENGTH_SHORT).show()
    }

    override fun DeleteWorkout(workout: Workout) {
        Toast.makeText(this.context, "delete", Toast.LENGTH_SHORT).show()
        db = Room.databaseBuilder(requireContext(), GymBuddyDatabase::class.java, "gymBuddyDatabase").allowMainThreadQueries().build()  //
        customWorkoutDao = db.customWorkoutDao()
        customWorkoutDao.delete(workout)
        var workout = customWorkoutDao.getAll()
        binding.rvwWorkouts.adapter = CustomWorkoutAdapter(workout, this)                                                   // adds the excercises list in the recyclerview
        binding.rvwWorkouts.layoutManager = LinearLayoutManager(context)
    }
}