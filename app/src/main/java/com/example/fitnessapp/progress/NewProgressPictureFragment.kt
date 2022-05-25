package com.example.fitnessapp.progress

import android.Manifest
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Base64.DEFAULT
import android.util.Base64.encodeToString
import android.view.*
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.room.Room
import com.example.fitnessapp.GymBuddyDatabase
import com.example.fitnessapp.R
import com.example.fitnessapp.databinding.FragmentNewProgresPictureBinding
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream
import java.util.*
import android.util.Base64
import java.util.Arrays.toString
import java.util.Objects.toString


class NewProgressPictureFragment : Fragment() {

    private lateinit var binding: FragmentNewProgresPictureBinding

    private lateinit var cameraPermissionResult: ActivityResultLauncher<String>
    private lateinit var pictureResult: ActivityResultLauncher<Void>
    private lateinit var snapshot: ImageView
    lateinit var db: GymBuddyDatabase
    private var pictureList: MutableList<Picture> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db = Room.databaseBuilder(requireContext(), GymBuddyDatabase::class.java, "gymBuddyDatabase").allowMainThreadQueries().build()

        binding = FragmentNewProgresPictureBinding.inflate(inflater)
        snapshot = binding.foto1
        binding.foto.setOnClickListener(this::tryToMakeSnapshot)
        binding.btnToGallery.setOnClickListener {
            val fragment: Fragment = ProgressPictureGalleryFragment(pictureList)
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
            snapshot.setImageBitmap(it)
            val tempPicture = Picture(Date().toString(), BitMapToString(it))
            pictureList.add(tempPicture)
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
