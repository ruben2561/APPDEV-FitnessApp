package com.example.fitnessapp.Workouts.customWorkouts

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
import androidx.room.Room
import com.example.fitnessapp.GymBuddyDatabase
import com.example.fitnessapp.MainActivity
import com.example.fitnessapp.R
import com.example.fitnessapp.Workouts.newWorkout.NewWorkoutAdapter
import com.example.fitnessapp.Workouts.workoutDisplay.WorkoutDisplayFragment
import com.example.fitnessapp.Workouts.newWorkout.NewWorkoutFragment
import com.example.fitnessapp.databinding.FragmentCustomWorkoutsBinding
import com.example.fitnessapp.exercises.Exercise
import com.google.android.material.snackbar.Snackbar

class CustomWorkoutsFragment : Fragment(), CustomWorkoutAdapter.OnItemClickListener{

    private lateinit var binding: FragmentCustomWorkoutsBinding
    lateinit var db: GymBuddyDatabase
    lateinit var customWorkoutDao: CustomWorkoutDao
    lateinit var customWorkout: MutableList<CustomWorkout>
    private lateinit var parentActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomWorkoutsBinding.inflate(layoutInflater)
        parentActivity = activity as MainActivity
        customWorkoutDao = parentActivity.db.customWorkoutDao()
        customWorkout = customWorkoutDao.getAll()                                                                              // gets all database items and puts it in a list

        /*customWorkoutDao.insert(listOf(CustomWorkout("schema 1", "20,50,88,100", 0),
                                 CustomWorkout("schema 2", "101,50,88,14,55", 0)))*/

        binding.rvwWorkouts.adapter = CustomWorkoutAdapter(customWorkout, this)                                                   // adds the excercises list in the recyclerview
        binding.rvwWorkouts.layoutManager = LinearLayoutManager(context)                                        // chooses what type of layout
        binding.rvwWorkouts.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))   // this puts a line between every item

        binding.newWorkoutButton.setOnClickListener{
            val fragment: Fragment = NewWorkoutFragment()
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            val containerId = R.id.fragment_container
            fragmentTransaction.replace(containerId, fragment).addToBackStack(null).commit()
        }

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
        //val intent = Intent(fragment, Activity3Recycler::class.java)
        //startActivity(intent)

        Toast.makeText(this.context, "long click on item: " + position, Toast.LENGTH_SHORT).show()
    }

    override fun DeleteWorkout(customWorkout: CustomWorkout) {
        val custom = customWorkout
        val snackbar = Snackbar
            .make(this.requireView(), "Selected: " + custom.name +". Confirm delete?", Snackbar.LENGTH_LONG)
            .setAction("YES") {
                db = Room.databaseBuilder(requireContext(), GymBuddyDatabase::class.java, "gymBuddyDatabase").allowMainThreadQueries().build()  //
                customWorkoutDao = db.customWorkoutDao()
                customWorkoutDao.delete(customWorkout)
                var customWorkout = customWorkoutDao.getAll()
                binding.rvwWorkouts.adapter = CustomWorkoutAdapter(customWorkout, this)                                                   // adds the excercises list in the recyclerview
                binding.rvwWorkouts.layoutManager = LinearLayoutManager(context)
                Snackbar.make(this.requireView(), "" + custom.name + " successfully deleted.", Snackbar.LENGTH_SHORT).show()
            }
        snackbar.show()


    }

    override fun EditWorkout(customWorkout: CustomWorkout) {
        val fragment: Fragment = NewWorkoutFragment(customWorkout)
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val containerId = R.id.fragment_container
        fragmentTransaction.replace(containerId, fragment).addToBackStack(null).commit()
    }

    override fun ShareWorkout(customWorkout: CustomWorkout){
        val input: String = customWorkout.exersicesId
        var result = input.split(",").map { it.trim() }
        val resultInt = result.map { it.toInt() }.toIntArray()
        db = Room.databaseBuilder(requireContext(), GymBuddyDatabase::class.java, "gymBuddyDatabase.db").createFromAsset("databases/gymBuddyDatabase.db").allowMainThreadQueries().build() // .createFromAsset("databases/exercisedatabase-db.db")
        val exerciseDao = db.exerciseDao()                                                                                                               //
        var exercises: List<Exercise> = exerciseDao.loadAllByIds(resultInt)
        //var stringToSend: String = "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀"
        var stringToSend: String = customWorkout.name + "\n" + "\n"

        for (item in exercises){
            stringToSend = stringToSend + item.name + "\n"
        }

        /*var i = 0
        while(i <= 4800){
            stringToSend = stringToSend + "ok\n" //"\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
            i++
        }
        stringToSend = stringToSend + "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀"*/


        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, stringToSend)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}