package com.example.fitnessapp.Workouts.newWorkout

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.fitnessapp.GymBuddyDatabase
import com.example.fitnessapp.MainActivity
import com.example.fitnessapp.R
import com.example.fitnessapp.Workouts.customWorkouts.CustomWorkout
import com.example.fitnessapp.Workouts.customWorkouts.CustomWorkoutsFragment
import com.example.fitnessapp.exercises.Exercise
import com.example.fitnessapp.databinding.FragmentCustomWorkoutsNewWorkoutBinding
import org.w3c.dom.Text

class NewWorkoutFragment(customWorkout: CustomWorkout = CustomWorkout("","",0)) : Fragment(), NewWorkoutAdapter.OnItemClickListener {

    private lateinit var binding: FragmentCustomWorkoutsNewWorkoutBinding
    private lateinit var parentActivity: MainActivity

    var choisesList = ArrayList<Exercise>()
    var filteredList = ArrayList<Exercise>()
    lateinit var recyclerList: MutableList<Exercise>
    var toggleState = 1; //0 when all exercises are showen, 1 when the chosen exercises are displayed
    var workoutToEdit = customWorkout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomWorkoutsNewWorkoutBinding.inflate(layoutInflater)

        if(workoutToEdit.name != ""){
            binding.workoutTitle.setText(workoutToEdit.name)
            val input: String = workoutToEdit.exersicesId
            var result = input.split(",").map { it.trim() }
            val resultInt = result.map { it.toInt() }.toIntArray()

            var db = Room.databaseBuilder(requireContext(), GymBuddyDatabase::class.java, "gymBuddyDatabase.db").createFromAsset("databases/gymBuddyDatabase.db").allowMainThreadQueries().build() // .createFromAsset("databases/exercisedatabase-db.db")
            var exerciseDao = db.exerciseDao()                                                                                                               //
            var exercises: List<Exercise> = exerciseDao.loadAllByIds(resultInt)

            choisesList.addAll(exercises)

            recyclerList = choisesList
            binding.rvwExercises.adapter = NewWorkoutAdapter(recyclerList, this)
            binding.rvwExercises.layoutManager = LinearLayoutManager(context)
        }

        binding.addExercise.setOnClickListener{

            if(toggleState == 0){
                binding.exerciseFilter.visibility = View.INVISIBLE
                recyclerList = choisesList
                binding.rvwExercises.adapter = NewWorkoutAdapter(recyclerList, this)
                binding.rvwExercises.layoutManager = LinearLayoutManager(context)
                toggleState = 1
            }

            else if(toggleState == 1){
                binding.exerciseFilter.visibility = View.VISIBLE
                val db = Room.databaseBuilder(requireContext(), GymBuddyDatabase::class.java, "gymBuddyDatabase").createFromAsset("databases/exercisedatabase-db.db").allowMainThreadQueries().build()
                val exerciseDao = db.exerciseDao()                                                                                                           //
                recyclerList = exerciseDao.getAll()
                binding.rvwExercises.adapter = NewWorkoutAdapter(recyclerList, this)                                              // adds the exercises list in the recyclerview
                binding.rvwExercises.layoutManager = LinearLayoutManager(context)                                        // chooses what type of layout
                toggleState = 0
            }

        }

        /**
         * saves workout in workout database if fields arent empty
         */
        binding.saveWorkout.setOnClickListener{
            parentActivity = activity as MainActivity
            var customWorkoutDao = parentActivity.db.customWorkoutDao()
            var name = binding.workoutTitle.text.toString()
            var exercisesIds = ""
            for (item in choisesList){
                if (item != choisesList[choisesList.size -1]){
                    exercisesIds = exercisesIds + item.id + ","
                }
                else {
                    exercisesIds = exercisesIds + item.id
                }
            }
            if(!name.contentEquals("") && exercisesIds != ""){
                customWorkoutDao.insertOne(CustomWorkout(name, exercisesIds, 0))
                Toast.makeText(this.context, "Workout saved!", Toast.LENGTH_LONG).show()

                val fragment: Fragment = CustomWorkoutsFragment()
                val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
                val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                val containerId = R.id.fragment_container
                fragmentTransaction.replace(containerId, fragment).addToBackStack(null).commit()
            }
            else{
                Toast.makeText(this.context, "enter workout name and exercises", Toast.LENGTH_LONG).show()
            }
        }

        if(toggleState == 1){
            binding.exerciseFilter.addTextChangedListener(object : TextWatcher,
                NewWorkoutAdapter.OnItemClickListener {                                //this is used to check the typed word which than filters the recyclerview to show to exercises that have a matching name or muscle group
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    filteredList = ArrayList()
                    val db = Room.databaseBuilder(requireContext(), GymBuddyDatabase::class.java, "gymBuddyDatabase").createFromAsset("databases/exercisedatabase-db.db").allowMainThreadQueries().build()
                    val exerciseDao = db.exerciseDao()
                    var tempList = exerciseDao.getAll()
                    for (item in tempList) {
                        if (item.name.lowercase().contains(s.toString().lowercase())) {
                            filteredList.add(item)
                        }
                        if (item.muscleGroup.lowercase().contains(s.toString().lowercase())) {
                            filteredList.add(item)
                        }
                    }
                    recyclerList = filteredList
                    binding.rvwExercises.adapter = NewWorkoutAdapter(recyclerList, this)
                    binding.rvwExercises.layoutManager = LinearLayoutManager(context)
                }

                override fun OnClick(position: Int) {
                    var filteredExercisename = filteredList[position].name
                    val db = Room.databaseBuilder(requireContext(), GymBuddyDatabase::class.java, "gymBuddyDatabase").createFromAsset("databases/exercisedatabase-db.db").allowMainThreadQueries().build()
                    val exerciseDao = db.exerciseDao()
                    showAddedExercise(filteredExercisename)
                    choisesList.add(exerciseDao.findByName(filteredExercisename))
                }
            })
        }

        return binding.root
    }

    override fun OnClick(position: Int) {
        val db = Room.databaseBuilder(requireContext(), GymBuddyDatabase::class.java, "gymBuddyDatabase").createFromAsset("databases/exercisedatabase-db.db").allowMainThreadQueries().build()
        val exerciseDao = db.exerciseDao()
        showAddedExercise(exerciseDao.loadByIds(position+1).name)
        choisesList.add(exerciseDao.loadByIds(position+1))
    }

    fun showAddedExercise(name: String){
        val db = Room.databaseBuilder(requireContext(), GymBuddyDatabase::class.java, "gymBuddyDatabase").createFromAsset("databases/exercisedatabase-db.db").allowMainThreadQueries().build()
        val exerciseDao = db.exerciseDao()
        val toast = Toast.makeText(this.context, name + " added to routine", Toast.LENGTH_SHORT)
        toast.show()

        /*
        this makes the toast message fade quicker
        source: https://stackoverflow.com/questions/3775074/set-toast-appear-length/9715422#9715422
         */
        val handler = Handler()
        handler.postDelayed(Runnable { toast.cancel() }, 750)
    }
}