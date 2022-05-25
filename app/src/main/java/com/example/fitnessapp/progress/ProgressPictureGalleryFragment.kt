package com.example.fitnessapp.progress


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.biometrics.BiometricManager
import android.os.Bundle
import android.util.Base64
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
import com.example.fitnessapp.Workouts.allWorkouts.AllWorkoutsAdapter
import com.example.fitnessapp.databinding.FragmentProgressPictureGalleryBinding
import java.util.*

class ProgressPictureGalleryFragment (private val pictureList: MutableList<Picture>) : Fragment(), PictureAdapter.OnItemClickListener {

    private lateinit var binding: FragmentProgressPictureGalleryBinding
    lateinit var db: GymBuddyDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        db = Room.databaseBuilder(requireContext(), GymBuddyDatabase::class.java, "gymBuddyDatabase").allowMainThreadQueries().build()
        binding = FragmentProgressPictureGalleryBinding.inflate(layoutInflater)

        binding.galleryGrid.adapter = PictureAdapter(pictureList,this)
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

    override fun OnClick(position: Int) {
        binding.selectedImage.setImageBitmap(StringToBitMap(pictureList[position].imageData))
    }

    override fun OnLongClick(position: Int) {
        TODO("Not yet implemented")
    }

    fun StringToBitMap(encodedString: String?): Bitmap? {
        return try {
            val encodeByte: ByteArray = Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e: Exception) {
            e.message
            null
        }
    }
}
