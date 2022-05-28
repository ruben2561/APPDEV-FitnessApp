package com.example.fitnessapp.exercises

import android.app.SearchManager
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.fitnessapp.MainActivity
import com.example.fitnessapp.R
import com.example.fitnessapp.databinding.FragmentExerciseDetailsBinding
import com.google.android.material.snackbar.Snackbar
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException


//API key: AIzaSyAkWKRP2go7kpSiMS5sY2N--3UvHgi_cTU
class ExercisesDetailsFragment(val exercise: Exercise) : Fragment() {

    lateinit var binding: FragmentExerciseDetailsBinding
    private lateinit var parentActivity: MainActivity
    lateinit var theDescription: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExerciseDetailsBinding.inflate(layoutInflater)
        parentActivity = activity as MainActivity
        binding.txtChosenExercise.text = exercise.name
        binding.txtSelectedExerciseMuscleGroup.text = exercise.muscleGroup
        binding.txtExerciseVideoURL.text = "Search the web for: " + exercise.name
        binding.txtExerciseVideoURL.setOnClickListener {
            val intent = Intent(Intent.ACTION_WEB_SEARCH)
            intent.putExtra(SearchManager.QUERY, exercise.name + " exercise")
            startActivity(intent)
        }
        val dw = description_webscrape(binding, exercise.name + " exercise description")
        dw.execute()
        return binding.root
    }

    private class description_webscrape(val binding: FragmentExerciseDetailsBinding, val query: String): AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg p0: Void?): Void? {
            lateinit var document: Document
            try{
                document = Jsoup.connect("https://www.google.com/search?q=" + query).get()
            }
            catch (e: IOException){
                e.printStackTrace()
            }

            var elements: Elements= document.getElementsByClass("hgKElc")
            val theDescription: String = elements.text()
            binding.txtExplanation.text = theDescription
            return null
            }

        }
    }
