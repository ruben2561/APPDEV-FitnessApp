package com.example.fitnessapp.Workouts.allWorkouts

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitnessapp.MainActivity
import com.example.fitnessapp.databinding.FragmentInfoBinding


class InfoFragment : Fragment(){

    private lateinit var binding: FragmentInfoBinding
    private lateinit var parentActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoBinding.inflate(layoutInflater)
        parentActivity = activity as MainActivity
        binding.imgKuLeuven.setOnClickListener{
            goToUrl("https://www.kuleuven.be/kuleuven/")
        }
        binding.imgUHasselt.setOnClickListener{
            goToUrl("https://www.uhasselt.be/")
        }
        return binding.root
    }


    private fun goToUrl(url: String) {
        val uriUrl: Uri = Uri.parse(url)
        val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
        startActivity(launchBrowser)
    }
}
