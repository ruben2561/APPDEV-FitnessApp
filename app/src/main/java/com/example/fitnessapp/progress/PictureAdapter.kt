package com.example.fitnessapp.progress

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.R
import java.util.*


class PictureAdapter (private val items: MutableList<Picture>) : RecyclerView.Adapter<PictureAdapter.PictureViewHolder>() {

    inner class PictureViewHolder(currentItemView: View) : RecyclerView.ViewHolder(currentItemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.picture, parent, false)

        return PictureViewHolder(view)

    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        val currentItem = items[position]
        holder.itemView.apply {
            findViewById<TextView>(R.id.txtDate).text = currentItem.name
            findViewById<ImageView>(R.id.galleryPicture).setImageBitmap(StringToBitMap(currentItem.imageData))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


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