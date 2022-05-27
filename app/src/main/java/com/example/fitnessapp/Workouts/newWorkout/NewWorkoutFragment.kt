package com.example.fitnessapp.Workouts.newWorkout

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
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
import com.example.fitnessapp.Workouts.customWorkouts.CustomWorkout
import com.example.fitnessapp.Workouts.customWorkouts.CustomWorkoutsFragment
import com.example.fitnessapp.databinding.FragmentCustomWorkoutsNewWorkoutBinding
import com.example.fitnessapp.exercises.Exercise
import com.google.android.material.snackbar.Snackbar
import java.util.*


class NewWorkoutFragment(customWorkout: CustomWorkout = CustomWorkout("","","","", 0)):
    Fragment(), NewWorkoutAdapter.OnItemClickListener, ChosenExercisesAdapter.OnItemClickListener {

    private lateinit var binding: FragmentCustomWorkoutsNewWorkoutBinding
    private lateinit var parentActivity: MainActivity
    var choicesListCustom = ArrayList<CustomExercise>()
    var choicesList = ArrayList<Exercise>()
    var filteredList = ArrayList<Exercise>()
    lateinit var recyclerList: MutableList<Exercise>
    private var toggleState = 1 //0 when all exercises are shown, 1 when the chosen exercises are displayed
    private var workoutToEdit = customWorkout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomWorkoutsNewWorkoutBinding.inflate(layoutInflater)
        parentActivity = activity as MainActivity
        // this takes the optional parameter and takes the title and exercises
        if(workoutToEdit.name != ""){
            binding.workoutTitle.setText(workoutToEdit.name)
            val input: String = workoutToEdit.exersicesId
            val result = input.split(",").map { it.trim() }
            val resultInt = result.map { it.toInt() }.toIntArray()
            val exerciseDao = parentActivity.db.exerciseDao()                                                                                                               //
            val exercises: List<Exercise> = exerciseDao.loadAllByIds(resultInt)
            for(item in exercises){
                val exer = exerciseDao.findByName(item.name)
                choicesList.add(exer)
                choicesListCustom.add(CustomExercise(exer.name, exer.muscleGroup,""))
            }
            recyclerList = choicesList
            binding.rvwExercises.adapter = ChosenExercisesAdapter(choicesListCustom, this)
            binding.rvwExercises.layoutManager = LinearLayoutManager(context)
        }
        binding.rvwExercises.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        binding.addExercise.setOnClickListener{

            if(toggleState == 0){
                binding.exerciseFilter.visibility = View.INVISIBLE
                recyclerList = choicesList
                binding.rvwExercises.adapter = ChosenExercisesAdapter(choicesListCustom, this)
                binding.rvwExercises.layoutManager = LinearLayoutManager(context)
                toggleState = 1
            }

            else if(toggleState == 1){
                binding.exerciseFilter.visibility = View.VISIBLE
                val exerciseDao = parentActivity.db.exerciseDao()                                                                                                           //
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
            val customWorkoutDao = parentActivity.db.customWorkoutDao()
            val name = binding.workoutTitle.text.toString()
            var exercisesIds = ""
            var exercisesRepsAndWeight = ""
            for (item in choicesList){
                exercisesIds = if (item != choicesList[choicesList.size -1]){
                    exercisesIds + item.id + ","
                } else {
                    exercisesIds + item.id
                }
            }
            for(item in choicesListCustom){
                exercisesRepsAndWeight = exercisesRepsAndWeight + item.repsAndWeight + ","
            }

            if(!name.contentEquals("") && exercisesIds != ""){
                val tempDate = DateFormat.format("dd-MM-yyyy", Date())
                customWorkoutDao.delete(workoutToEdit)
                customWorkoutDao.insertOne(CustomWorkout(name, exercisesIds, tempDate.toString(), exercisesRepsAndWeight, 0))
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
                    val exerciseDao = parentActivity.db.exerciseDao()
                    val tempList = exerciseDao.getAll()
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

                override fun onClick(position: Int) {
                    val filteredExercisename = filteredList[position].name
                    val exerciseDao = parentActivity.db.exerciseDao()
                    showAddedExercise(filteredExercisename)
                    choicesList.add(exerciseDao.findByName(filteredExercisename))
                    if(!choicesList.contains(exerciseDao.findByName(filteredExercisename))){
                        val exer = exerciseDao.findByName(filteredExercisename)
                        choicesList.add(exer)
                        choicesListCustom.add(CustomExercise(exer.name, exer.muscleGroup,""))
                    }
                }

                override fun onLongClick(position: Int) {
                    TODO("Not yet implemented")
                }
            })
        }

        return binding.root
    }

    override fun onClick(position: Int) {
        if( toggleState == 0){
            val exerciseDao = parentActivity.db.exerciseDao()
            showAddedExercise(exerciseDao.loadByIds(position+1).name)
            if(!choicesList.contains(exerciseDao.loadByIds(position+1))){
                val exer = exerciseDao.loadByIds(position+1)
                choicesList.add(exer)
                choicesListCustom.add(CustomExercise(exer.name, exer.muscleGroup,""))
            }
        }
    }

    override fun onLongClick(position: Int) {
        val snack = Snackbar
            .make(this.requireView(), "Selected: $position. Confirm delete?", Snackbar.LENGTH_LONG)
            .setAction("YES") {
                choicesList.removeAt(position)
                recyclerList = choicesList
                binding.rvwExercises.adapter = NewWorkoutAdapter(recyclerList, this)
                binding.rvwExercises.layoutManager = LinearLayoutManager(context)
                Snackbar.make(this.requireView(), "Picture successfully deleted.", Snackbar.LENGTH_SHORT).show()
            }
        snack.show()
    }

    fun showAddedExercise(name: String){
        parentActivity.db.exerciseDao()
        val toast = Toast.makeText(this.context, "$name added to routine", Toast.LENGTH_SHORT)
        toast.show()

        /*
        this makes the toast message fade quicker
        source: https://stackoverflow.com/questions/3775074/set-toast-appear-length/9715422#9715422
         */
        Handler(Looper.getMainLooper()).postDelayed({
            toast.cancel()
        }, 3000)
    }
}