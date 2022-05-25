package com.example.fitnessapp.progress


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.biometrics.BiometricManager
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.example.fitnessapp.GymBuddyDatabase
import com.example.fitnessapp.MainActivity
import com.example.fitnessapp.R
import com.example.fitnessapp.Workouts.allWorkouts.AllWorkoutsAdapter
import com.example.fitnessapp.databinding.FragmentProgressPictureGalleryBinding
import java.util.*

class ProgressPictureGalleryFragment () : Fragment(), PictureAdapter.OnItemClickListener {

    private lateinit var binding: FragmentProgressPictureGalleryBinding
    lateinit var db: GymBuddyDatabase
    private lateinit var parentActivity: MainActivity
    private lateinit var pictureDao: PictureDao
    private lateinit var pictureList: MutableList<Picture>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProgressPictureGalleryBinding.inflate(layoutInflater)
        parentActivity = activity as MainActivity
        pictureDao = parentActivity.db.pictureDao()
        pictureList = pictureDao.getAll()
        binding.galleryGrid.adapter = PictureAdapter(pictureList,this)
        val gridLayoutManager = GridLayoutManager(context, 3)
        binding.galleryGrid.layoutManager = gridLayoutManager

        return binding.root
    }

    override fun OnClick(position: Int) {
        val toast = Toast.makeText(this.context, "position: " + position, Toast.LENGTH_SHORT).show()
        val fragment: Fragment = SelectedImageFragment(position + 1)
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val containerId = R.id.fragment_container
        fragmentTransaction.replace(containerId, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun OnLongClick(position: Int) {
        val toast = Toast.makeText(this.context, "long click on position: " + position, Toast.LENGTH_SHORT).show()
        TODO("Not yet implemented")
    }
}
