package com.example.fitnessapp.progress

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.R
import com.example.fitnessapp.Workouts.newWorkout.NewWorkoutAdapter
import java.util.*


class PictureAdapter(private val imageNames: MutableList<Date>, private val imagesPhoto: MutableList<Bitmap>, private var onItemClickListener: OnItemClickListener):
    RecyclerView.Adapter<PictureAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.exercise, parent, false)
        return ViewHolder(view, onItemClickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = items[position]
        holder.bind(exercise)
    }

    class ViewHolder(itemView: View, onItemClickListener: OnItemClickListener): RecyclerView.ViewHolder(itemView){

        init{
            itemView.setOnClickListener{
                //val snack: String = "item position clicked: $adapterPosition"
                //Snackbar.make(itemView, snack, Snackbar.LENGTH_SHORT).show()
                onItemClickListener.OnClick(adapterPosition)
            }
        }

        fun bind(exercise: Exercise){
            itemView.apply{findViewById<TextView>(R.id.txtExerciseName).text = exercise.name}
            itemView.apply{findViewById<TextView>(R.id.txtMuscleGroup).text = exercise.muscleGroup}
        }
    }

    interface OnItemClickListener{
        fun OnClick(position: Int)
    }


}

/*class PictureAdapter (private val imageNames: MutableList<Date>, private val imagesPhoto: MutableList<Bitmap>, private var onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<PictureAdapter.PictureViewHolder>() {

    inner class PictureViewHolder(currentItemView: View, onItemClickListener: NewWorkoutAdapter.OnItemClickListener) : RecyclerView.ViewHolder(currentItemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.picture, parent, false)

        return PictureViewHolder(view, onItemClickListener)

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

    interface OnItemClickListener{
        fun OnClick(position: Int)
    }
}*/

