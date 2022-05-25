package com.example.fitnessapp.Workouts.allWorkouts

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.fitnessapp.GymBuddyDatabase
import com.example.fitnessapp.Workouts.DefaultWorkout
import com.example.fitnessapp.Workouts.DefaultWorkoutDao
import com.example.fitnessapp.databinding.FragmentAllWorkoutsBinding

class AllWorkoutsFragment : Fragment(), AllWorkoutsAdapter.OnItemClickListener{

    private lateinit var binding: FragmentAllWorkoutsBinding
    lateinit var db: GymBuddyDatabase
    lateinit var defaultWorkoutDao: DefaultWorkoutDao
    lateinit var defaultWorkout: MutableList<DefaultWorkout>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllWorkoutsBinding.inflate(layoutInflater)
        //db = Room.databaseBuilder(requireContext(), GymBuddyDatabase::class.java, "gymBuddyDatabase").allowMainThreadQueries().build()  //
        db = Room.databaseBuilder(requireContext(), GymBuddyDatabase::class.java, "gymBuddyDatabase").createFromAsset("databases/gymBuddyDatabase.db").allowMainThreadQueries().build()
        defaultWorkoutDao = db.defaultWorkoutDao()
        defaultWorkout = defaultWorkoutDao.getAll()                                                                              // gets all database items and puts it in a list


        binding.rvwWorkouts.adapter = AllWorkoutsAdapter(defaultWorkout, this)                                                   // adds the exercises list in the recyclerview
        binding.rvwWorkouts.layoutManager = LinearLayoutManager(context)                                        // chooses what type of layout
        binding.rvwWorkouts.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))   // this puts a line between every item

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



}

/*workoutDao.insert(listOf(
            Workout("Biceps", "10,208,200", 0),
            Workout("Back", "888,103,861,358", 0),
            Workout("Triceps", "236,152,851,198", 0),
            Workout("Chest", "369,883,590", 0),
            Workout("Legs", "74,474,73,162,579", 0),
            Workout("Shoulders", "270,960,150,293", 0),
            Workout("Abs", "614,216,640,564", 0),
            Workout("Forearms", "808,895", 0)))
            */