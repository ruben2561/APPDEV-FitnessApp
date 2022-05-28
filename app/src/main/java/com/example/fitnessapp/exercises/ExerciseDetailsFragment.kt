package com.example.fitnessapp.exercises

import android.app.SearchManager
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitnessapp.databinding.FragmentExerciseDetailsBinding
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException

class ExercisesDetailsFragment(val exercise: Exercise) : Fragment() {

    lateinit var binding: FragmentExerciseDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExerciseDetailsBinding.inflate(layoutInflater)
        binding.txtChosenExercise.text = exercise.name
        binding.txtExerciseVideoURL.text = "Search the web for: " + exercise.name
        binding.txtExerciseVideoURL.setOnClickListener {
            val intent = Intent(Intent.ACTION_WEB_SEARCH)
            intent.putExtra(SearchManager.QUERY, exercise.name + " exercise")
            startActivity(intent)
        }
        val dw = DescriptionWebscrape(binding, exercise.name)
        dw.execute()
        return binding.root
    }

    private class DescriptionWebscrape(val binding: FragmentExerciseDetailsBinding, val query: String): AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg p0: Void?): Void? {
            lateinit var descriptionDoc: Document
            try{
                descriptionDoc = Jsoup.connect("https://www.google.com/search?q=$query exercise description").get()
            }
            catch (e: IOException){
                e.printStackTrace()
            }
            val descriptionElements: Elements= descriptionDoc.getElementsByClass("hgKElc")
            val theDescription: String = descriptionElements.text()
            binding.txtExplanation.text = theDescription
            return null
            }
        }
    }
