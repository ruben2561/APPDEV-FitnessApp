package com.example.fitnessapp.progress

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.R
import java.util.*


class PictureAdapter (private val imageNames: MutableList<Date>, private val imagesPhoto: MutableList<Bitmap>) : RecyclerView.Adapter<PictureAdapter.PictureViewHolder>() {

    inner class PictureViewHolder(currentItemView: View) : RecyclerView.ViewHolder(currentItemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.picture, parent, false)

        return PictureViewHolder(view)

    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        val currentPictureDate = imageNames[position]
        val currentPictureBitmap = imagesPhoto[position]
        holder.itemView.apply {
            findViewById<TextView>(R.id.txtDate).text = currentPictureDate.toString()
            findViewById<ImageView>(R.id.galleryPicture).setImageBitmap(currentPictureBitmap)
        }
    }

    override fun getItemCount(): Int {
        return imageNames.size
    }
}