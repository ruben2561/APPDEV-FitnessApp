package com.example.fitnessapp.progress


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fitnessapp.GymBuddyDatabase
import com.example.fitnessapp.MainActivity
import com.example.fitnessapp.R
import com.example.fitnessapp.databinding.FragmentProgressPictureGalleryBinding
import com.google.android.material.snackbar.Snackbar

class ProgressPictureGalleryFragment () : Fragment(), PictureAdapter.OnItemClickListener {

    private lateinit var binding: FragmentProgressPictureGalleryBinding
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

    override fun onClick(picture: Picture) {
        val fragment: Fragment = SelectedImageFragment(picture)
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val containerId = R.id.fragment_container
        fragmentTransaction.replace(containerId, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onLongClick(picture: Picture) {
        val snackbar = Snackbar
            .make(this.requireView(), "Selected: " + picture.name+". Confirm delete?", Snackbar.LENGTH_LONG)
            .setAction("YES") {
                pictureDao.delete(picture)
                pictureList = pictureDao.getAll()
                binding.galleryGrid.adapter = PictureAdapter(pictureList,this)
                val gridLayoutManager = GridLayoutManager(context, 3)
                binding.galleryGrid.layoutManager = gridLayoutManager
                Snackbar.make(this.requireView(), "Picture successfully deleted.", Snackbar.LENGTH_SHORT).show()
            }
        snackbar.show()
    }
}
