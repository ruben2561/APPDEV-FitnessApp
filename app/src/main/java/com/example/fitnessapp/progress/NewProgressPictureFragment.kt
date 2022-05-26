package com.example.fitnessapp.progress

import android.Manifest
import android.graphics.Bitmap
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.fitnessapp.MainActivity
import com.example.fitnessapp.R
import com.example.fitnessapp.databinding.FragmentNewProgresPictureBinding
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream
import java.util.*


class NewProgressPictureFragment : Fragment() {

    private lateinit var binding: FragmentNewProgresPictureBinding

    private lateinit var cameraPermissionResult: ActivityResultLauncher<String>
    private lateinit var pictureResult: ActivityResultLauncher<Void>
    private lateinit var snapshot: ImageView
    private lateinit var parentActivity: MainActivity
    private lateinit var pictureDao: PictureDao
    private lateinit var tempBitmap: Bitmap
    private var pictureList: MutableList<Picture> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        parentActivity = activity as MainActivity
        pictureDao = parentActivity.db.pictureDao()
        binding = FragmentNewProgresPictureBinding.inflate(inflater)
        snapshot = binding.foto1

        binding.foto1.setOnClickListener(this::tryToMakeSnapshot)

        binding.btnToGallery.setOnClickListener {
            val fragment: Fragment = ProgressPictureGalleryFragment()
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            val containerId = R.id.fragment_container
            fragmentTransaction.replace(containerId, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
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
            tempBitmap = it
        }

        binding.savePhoto.setOnClickListener {
            if(binding.editTextWeight.text.toString() != "" && tempBitmap != null){
                val tempDate = DateFormat.format("dd-MM-yyyy", Date())
                val tempWeight = binding.editTextWeight.text.toString()
                val tempPicture = Picture(tempDate.toString(), BitMapToString(tempBitmap), tempWeight)
                /*var tempDate = DateFormat.format("dd-MM-yyyy\nHH.mm.ss", Date()).toString()
                val tempPicture = Picture(tempDate, BitMapToString(it))*/
                pictureDao.insert(tempPicture)
                Snackbar.make(binding.root, "Progress Picture Saved", Snackbar.LENGTH_SHORT).show()
            }
            else{
                Snackbar.make(binding.root, "Take a picture and enter weight", Snackbar.LENGTH_SHORT).show()
            }

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
}

fun BitMapToString(bitmap: Bitmap): String {
    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
    val b: ByteArray = baos.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}

