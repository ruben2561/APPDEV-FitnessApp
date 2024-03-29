package com.example.fitnessapp.progress

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fitnessapp.MainActivity
import com.example.fitnessapp.databinding.FragmentSelectedImageBinding

class SelectedImageFragment(val picture: Picture) : Fragment() {

    private lateinit var binding: FragmentSelectedImageBinding
    private lateinit var parentActivity: MainActivity
    private lateinit var pictureDao: PictureDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectedImageBinding.inflate(layoutInflater)
        parentActivity = activity as MainActivity
        pictureDao = parentActivity.db.pictureDao()
        val selectedImage = pictureDao.findByDate(picture.name)
        binding.txtSelectedImageName.text = selectedImage.name.take(10)
        binding.txtSelectedImageWeight.text = "Weight: " + selectedImage.weight + "Kg"
        binding.imgSelectedImage.setImageBitmap(stringToBitMap(selectedImage.imageData))
        return binding.root
    }

    private fun stringToBitMap(encodedString: String?): Bitmap? {
        return try {
            val encodeByte: ByteArray = Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e: Exception) {
            e.message
            null
        }
    }
}