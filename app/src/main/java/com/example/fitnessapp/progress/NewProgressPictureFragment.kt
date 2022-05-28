package com.example.fitnessapp.progress

import android.Manifest
import android.graphics.Bitmap
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    private lateinit var parentActivity: MainActivity
    private lateinit var pictureDao: PictureDao
    private var tempBitmap: Bitmap? = null
    private val symbols = "0123456789"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewProgresPictureBinding.inflate(inflater)
        parentActivity = activity as MainActivity
        pictureDao = parentActivity.db.pictureDao()
        binding.foto1.setOnClickListener(this::tryToMakeSnapshot)
        binding.btnToGallery.setOnClickListener {
            val fragment: Fragment = ProgressPictureGalleryFragment()
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            val containerId = R.id.fragment_container
            fragmentTransaction.replace(containerId, fragment).addToBackStack(null).commit()
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
            binding.foto1.setImageBitmap(it)
            tempBitmap = it
        }

        binding.savePhoto.setOnClickListener {
            val textWeight = binding.editTextWeight.text.toString()
            if(textWeight != ""){
                var weightIsOnlyDigits = true
                for (item in textWeight){
                    if (item !in symbols){
                        weightIsOnlyDigits = false
                    }
                }
                if (weightIsOnlyDigits && tempBitmap != null){
                    val tempDate = DateFormat.format("dd-MM-yyyy", Date())
                    val tempWeight = binding.editTextWeight.text.toString()
                    val tempPicture = Picture(tempDate.toString(), bitMapToString(tempBitmap), tempWeight)
                    pictureDao.insert(tempPicture)
                    Snackbar.make(binding.root, "Progress Picture Saved", Snackbar.LENGTH_SHORT).show()
                }
                if (!weightIsOnlyDigits){
                    Snackbar.make(binding.root, "Weight can only consist of digits", Snackbar.LENGTH_SHORT).show()
                }
                if (tempBitmap == null){
                    Snackbar.make(binding.root, "Make a picture before trying to save it", Snackbar.LENGTH_SHORT).show()
                }
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

fun bitMapToString(bitmap: Bitmap?): String {
    val bas = ByteArrayOutputStream()
    bitmap?.compress(Bitmap.CompressFormat.PNG, 100, bas)
    val b: ByteArray = bas.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}

