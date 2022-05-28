package com.example.fitnessapp.exercises

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.fitnessapp.databinding.FragmentExercisesBinding

class ExercisesFragment : Fragment(), ExerciseAdapter.OnItemClickListener {

    private lateinit var binding: FragmentExercisesBinding
    private lateinit var exerciseDao: ExerciseDao
    private lateinit var parentActivity: MainActivity
    private lateinit var exercises: MutableList<Exercise>
    private var filteredList: ArrayList<Exercise> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExercisesBinding.inflate(layoutInflater)
        parentActivity = activity as MainActivity
        exerciseDao = parentActivity.db.exerciseDao()                                                                                                               //
        exercises = exerciseDao.getAll()                                                                                             // gets all database items and puts it in a list
        binding.rvwExercises.adapter = ExerciseAdapter(exercises,this)                                                // adds the exercises list in the recyclerview
        binding.rvwExercises.layoutManager = LinearLayoutManager(context)                                        // chooses what type of layout
        binding.rvwExercises.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))   // this puts a line between every item

        binding.filter.addTextChangedListener(object : TextWatcher {                                //this is used to check the typed word which than filters the recyclerview to show to exercises that have a matching name or muscle group
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                filteredList.clear()                                                          // clear arraylist where the matches item are stored
                for (item in exercises) {                                                     // for loop checks for match with every item
                    if (item.name.lowercase().contains(s.toString().lowercase())) {           // name match check
                        filteredList.add(item)                                                //
                    }                                                                         //
                    else if (item.muscleGroup.lowercase().contains(s.toString().lowercase())) {    // muscle group match check
                        filteredList.add(item)                                                //
                    }                                                                         //
                }                                                                             //
                binding.rvwExercises.adapter = ExerciseAdapter(filteredList,this@ExercisesFragment)                  // redraws the recyclerview
                binding.rvwExercises.layoutManager = LinearLayoutManager(context)             //
            }                                                                                 //
        })                                                                                    //

        return binding.root
    }

    override fun onClick(exercise: Exercise){
        val fragment: Fragment = ExercisesDetailsFragment(exercise)
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val containerId = R.id.fragment_container
        fragmentTransaction.replace(containerId, fragment).addToBackStack(null).commit()
    }
}