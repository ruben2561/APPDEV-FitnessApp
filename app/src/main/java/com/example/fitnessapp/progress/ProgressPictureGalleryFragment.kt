package com.example.fitnessapp.progress


import android.graphics.Bitmap
import android.hardware.biometrics.BiometricManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.example.fitnessapp.GymBuddyDatabase
import com.example.fitnessapp.R
import com.example.fitnessapp.databinding.FragmentProgressPictureGalleryBinding
import java.util.*

class ProgressPictureGalleryFragment (private val snapshotList: MutableList<Bitmap>, private val dateList: MutableList<Date>) : Fragment() {

    private lateinit var binding: FragmentProgressPictureGalleryBinding
    lateinit var db: GymBuddyDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db = Room.databaseBuilder(requireContext(), GymBuddyDatabase::class.java, "gymBuddyDatabase").allowMainThreadQueries().build()
        binding = FragmentProgressPictureGalleryBinding.inflate(layoutInflater)
        if(snapshotList.size>0) {
            binding.selectedImage.setImageBitmap(snapshotList[snapshotList.size - 1]) //Display last image
        }
        val pictureAdapter = PictureAdapter(dateList, snapshotList)
        binding.galleryGrid.adapter = pictureAdapter
        val gridLayoutManager = GridLayoutManager(context, 3)
        binding.galleryGrid.layoutManager = gridLayoutManager
        binding.btnReturn.setOnClickListener {
            val fragment: Fragment = NewProgressPictureFragment()
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            val containerId = R.id.fragment_container
            fragmentTransaction.replace(containerId, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        return binding.root
    }
}