package com.example.fitnessapp.progress

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fitnessapp.MainActivity
import com.example.fitnessapp.R
import com.example.fitnessapp.databinding.FragmentProgressPictureGalleryBinding
import com.example.fitnessapp.databinding.FragmentSelectedImageBinding


class SelectedImageFragment(val position: Int) : Fragment() {

    private lateinit var binding: FragmentSelectedImageBinding
    private lateinit var parentActivity: MainActivity
    private lateinit var pictureDao: PictureDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectedImageBinding.inflate(layoutInflater)
        parentActivity = activity as MainActivity
        pictureDao = parentActivity.db.pictureDao()
        var selectedImage = pictureDao.loadByIds(position)
        binding.selectedImageName.text = selectedImage.name
        binding.imageView.setImageBitmap(StringToBitMap(selectedImage.imageData))

        //binding.imageView.setOn
        return binding.root
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