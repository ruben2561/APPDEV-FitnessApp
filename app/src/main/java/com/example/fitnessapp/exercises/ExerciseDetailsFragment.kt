package com.example.fitnessapp.exercises

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitnessapp.MainActivity
import com.example.fitnessapp.databinding.FragmentExerciseDetailsBinding


class ExercisesDetailsFragment(val exercise: Exercise) : Fragment() {

    private lateinit var binding: FragmentExerciseDetailsBinding
    private lateinit var parentActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExerciseDetailsBinding.inflate(layoutInflater)
        parentActivity = activity as MainActivity
        binding.txtChosenExercise.text = exercise.name
        return binding.root
    }
}