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
        binding.rvwExercises.layoutManager = LinearLayoutManager(context)
        binding.rvwExercises.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        binding.filter.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                filteredList.clear()
                for (item in exercises) {
                    if (item.name.lowercase().contains(s.toString().lowercase())) {
                        filteredList.add(item)
                    }
                    else if (item.muscleGroup.lowercase().contains(s.toString().lowercase())) {
                        filteredList.add(item)
                    }
                }
                binding.rvwExercises.adapter = ExerciseAdapter(filteredList,this@ExercisesFragment)                  // redraws the recyclerview
                binding.rvwExercises.layoutManager = LinearLayoutManager(context)
            }
        })

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