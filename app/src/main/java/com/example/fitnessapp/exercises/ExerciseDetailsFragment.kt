package com.example.fitnessapp.exercises

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitnessapp.MainActivity
import com.example.fitnessapp.databinding.FragmentExerciseDetailsBinding


//API key: AIzaSyAkWKRP2go7kpSiMS5sY2N--3UvHgi_cTU
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
        val intent = Intent(Intent.ACTION_WEB_SEARCH)
        intent.putExtra(SearchManager.QUERY, exercise.name + " exercise")
        startActivity(intent)


        return binding.root
    }
}
