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
import com.example.fitnessapp.MainActivity
import com.example.fitnessapp.R
import com.example.fitnessapp.Workouts.newWorkout.NewWorkoutFragment
import com.example.fitnessapp.Workouts.workoutDisplay.CustomWorkoutDisplayFragment
import com.example.fitnessapp.databinding.FragmentCustomWorkoutsBinding
import com.example.fitnessapp.exercises.Exercise
import com.google.android.material.snackbar.Snackbar

class CustomWorkoutsFragment : Fragment(), CustomWorkoutAdapter.OnItemClickListener{

    private lateinit var binding: FragmentCustomWorkoutsBinding
    private lateinit var customWorkoutDao: CustomWorkoutDao
    private lateinit var customWorkout: MutableList<CustomWorkout>
    private lateinit var parentActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomWorkoutsBinding.inflate(layoutInflater)
        parentActivity = activity as MainActivity
        customWorkoutDao = parentActivity.db.customWorkoutDao()
        customWorkout = customWorkoutDao.getAll()                                                                              // gets all database items and puts it in a list

        binding.rvwWorkouts.adapter = CustomWorkoutAdapter(customWorkout, this)                                                   // adds the exercises list in the recyclerview
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

    override fun onClick(customWorkout: CustomWorkout) {
        val fragment: Fragment = CustomWorkoutDisplayFragment(customWorkout)
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val containerId = R.id.fragment_container
        fragmentTransaction.replace(containerId, fragment).addToBackStack(null).commit()
    }

    override fun onLongClick(position: Int) {
        //val intent = Intent(fragment, Activity3Recycler::class.java)
        //startActivity(intent)

        Toast.makeText(this.context, "long click on item: " + position, Toast.LENGTH_SHORT).show()
    }

    override fun deleteWorkout(customWorkout: CustomWorkout) {
        val custom = customWorkout
        val snack = Snackbar
            .make(this.requireView(), "Selected: " + custom.name +". Confirm delete?", Snackbar.LENGTH_LONG)
            .setAction("YES") {
                customWorkoutDao = parentActivity.db.customWorkoutDao()
                customWorkoutDao.delete(customWorkout)
                val customWorkouts = customWorkoutDao.getAll()
                binding.rvwWorkouts.adapter = CustomWorkoutAdapter(customWorkouts, this)                                                   // adds the exercises list in the recyclerview
                binding.rvwWorkouts.layoutManager = LinearLayoutManager(context)
                Snackbar.make(this.requireView(), "" + custom.name + " successfully deleted.", Snackbar.LENGTH_SHORT).show()
            }
        snack.show()


    }

    override fun editWorkout(customWorkout: CustomWorkout) {
        val fragment: Fragment = NewWorkoutFragment(customWorkout)
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val containerId = R.id.fragment_container
        fragmentTransaction.replace(containerId, fragment).addToBackStack(null).commit()
    }

    override fun shareWorkout(customWorkout: CustomWorkout){
        val input: String = customWorkout.exersicesId
        val result = input.split(",").map { it.trim() }
        val resultInt = result.map { it.toInt() }.toIntArray()

        val input2: String = customWorkout.repsAndWeight
        val result2 = input2.split(",").map { it.trim() }
        var teller = 0

        val exerciseDao = parentActivity.db.exerciseDao()                                                                                                               //
        val exercises: List<Exercise> = exerciseDao.loadAllByIds(resultInt)

        var stringToSend: String = customWorkout.name + "\n" + "----------------------" + "\n"

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
}



/*var i = 0
        while(i <= 4800){
            stringToSend = stringToSend + "ok\n" //"\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
            i++
        }
        stringToSend = stringToSend + "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀"*/