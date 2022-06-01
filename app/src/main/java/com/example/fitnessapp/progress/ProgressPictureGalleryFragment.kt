package com.example.fitnessapp.progress

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fitnessapp.MainActivity
import com.example.fitnessapp.R
import com.example.fitnessapp.databinding.FragmentProgressPictureGalleryBinding
import com.google.android.material.snackbar.Snackbar

class ProgressPictureGalleryFragment : Fragment(), PictureAdapter.OnItemClickListener {

    private lateinit var binding: FragmentProgressPictureGalleryBinding
    private lateinit var parentActivity: MainActivity
    private lateinit var pictureDao: PictureDao
    private lateinit var pictureList: MutableList<Picture>
    private lateinit var cameraPermissionResult: ActivityResultLauncher<String>
    private lateinit var pictureResult: ActivityResultLauncher<Void>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProgressPictureGalleryBinding.inflate(layoutInflater)
        parentActivity = activity as MainActivity
        pictureDao = parentActivity.db.pictureDao()
        pictureList = pictureDao.getAll()
        binding.galleryGrid.adapter = PictureAdapter(pictureList,this)
        if (pictureList.isNotEmpty()){
            binding.textView.visibility = View.INVISIBLE
        }
        val gridLayoutManager = GridLayoutManager(context, 3)
        binding.galleryGrid.layoutManager = gridLayoutManager
        binding.btnToNewProgressPictureFragment.setOnClickListener{
            tryToMakeSnapshot(this.requireView())
        }
        cameraPermissionResult =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { succeeded ->
                if (succeeded) {
                    makeSnapshot()
                } else {
                    Snackbar.make(binding.root, "Request denied...", Snackbar.LENGTH_SHORT).show()
                }
            }

        pictureResult = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            // photo taken!
            val fragment: Fragment = NewProgressPictureFragment(it)
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            val containerId = R.id.fragment_container
            fragmentTransaction.replace(containerId, fragment).addToBackStack(null).commit()
        }
        return binding.root
    }

    private fun makeSnapshot() {
        pictureResult.launch(null)
    }

    private fun tryToMakeSnapshot(view: View) {
        if (PermissionChecker.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.CAMERA
            ) != PermissionChecker.PERMISSION_GRANTED
        ) {
            cameraPermissionResult.launch(Manifest.permission.CAMERA)
        } else {
            makeSnapshot()
        }
    }
    override fun onClick(picture: Picture) {
        val fragment: Fragment = SelectedImageFragment(picture)
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val containerId = R.id.fragment_container
        fragmentTransaction.replace(containerId, fragment).addToBackStack(null).commit()
    }

    override fun onLongClick(picture: Picture) {
        val snackbar = Snackbar
            .make(this.requireView(), "Confirm delete?", Snackbar.LENGTH_LONG)
            .setAction("YES") {
                pictureDao.delete(picture)
                pictureList = pictureDao.getAll()
                binding.galleryGrid.adapter = PictureAdapter(pictureList,this)
                val gridLayoutManager = GridLayoutManager(context, 3)
                binding.galleryGrid.layoutManager = gridLayoutManager
                Snackbar.make(this.requireView(), "Picture successfully deleted.", Snackbar.LENGTH_SHORT).show()
                if (pictureList.isEmpty()){
                    binding.textView.visibility = View.VISIBLE
                }
            }
        snackbar.show()
    }
}
