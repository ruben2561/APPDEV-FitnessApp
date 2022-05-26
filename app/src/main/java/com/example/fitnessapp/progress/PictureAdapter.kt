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


class PictureAdapter(private var items: List<Picture>, private var onItemClickListener: OnItemClickListener):
    RecyclerView.Adapter<PictureAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.picture, parent, false)
        return ViewHolder(view, onItemClickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPicture = items[position]
        holder.bind(currentPicture)
        holder.currentPicture = currentPicture
    }

    class ViewHolder(itemView: View, onItemClickListener: OnItemClickListener): RecyclerView.ViewHolder(itemView) {
        lateinit var currentPicture: Picture
        init {
            itemView.setOnClickListener {
                onItemClickListener.OnClick(currentPicture)
            }

            itemView.setOnLongClickListener {
                onItemClickListener.OnLongClick(currentPicture)
                return@setOnLongClickListener true
            }
        }

        fun bind(currentPicture: Picture) {
            itemView.apply { findViewById<TextView>(R.id.txtDate).text = currentPicture.name }
            itemView.apply { findViewById<TextView>(R.id.txtWeight).text = "Weight: " + currentPicture.weight + "Kg"}
            itemView.apply {
                findViewById<ImageView>(R.id.galleryPicture).setImageBitmap(StringToBitMap(currentPicture.imageData))
            }

        }
    }
    interface OnItemClickListener{
        fun OnClick(picture: Picture)
        fun OnLongClick(picture: Picture)
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