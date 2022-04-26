package com.example.fitnessapp

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Build
import android.content.pm.PackageManager
import android.content.ContentValues
import android.provider.MediaStore
import android.content.Intent
import android.widget.Toast
import android.net.Uri
import android.view.View
import android.widget.Button
import android.widget.ImageView
import java.util.ArrayList

class NewProgressPictureFragment : AppCompatActivity() {
    var lijst = ArrayList<Uri?>()
    var mCaptureBtn: Button? = null
    var mImageView: ImageView? = null
    var image_uri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_new_progres_picture)
        mImageView = findViewById(R.id.foto1)
        mCaptureBtn = findViewById(R.id.foto)
    }

    fun onClick(view: View?) {
        //if system os is >= marshmallow, request runtime permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_DENIED ||
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED
            ) {
                //permission not enabled, request it
                val permission =
                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                //show popup to request permissions
                requestPermissions(permission, PERMISSION_CODE)
            } else {
                //permission already granted
                openCamera()
            }
        } else {
            //system os < marshmallow
            openCamera()
        }
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        lijst.add(image_uri)
        //Camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }

    //handling permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        //this method is called, when user presses Allow or Deny from Permission Request Popup
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    //permission from popup was granted
                    openCamera()
                } else {
                    //permission from popup was denied
                    Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //called when image was captured from camera
        if (resultCode == RESULT_OK) {
            //set the image captured to our ImageView
            mImageView!!.setImageURI(image_uri)
        }
    }

    companion object {
        private const val PERMISSION_CODE = 1000
        private const val IMAGE_CAPTURE_CODE = 1001
    }
}