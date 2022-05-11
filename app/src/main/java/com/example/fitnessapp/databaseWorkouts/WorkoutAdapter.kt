package com.example.fitnessapp.databaseWorkouts


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.AllWorkoutsFragment
import com.example.fitnessapp.R
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.android.material.snackbar.Snackbar

class WorkoutAdapter(val items: List<Workout>) : RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

    inner class WorkoutViewHolder(currentItemView: View) : RecyclerView.ViewHolder(currentItemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.workout, parent, false)

        return WorkoutViewHolder(view)

    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val currentWorkoutItem = items[position]
        holder.itemView.apply {
            findViewById<TextView>(R.id.txtWorkoutName).text = currentWorkoutItem.name
            findViewById<TextView>(R.id.txtNumberOfExcercises).text = "Number of excersices: " + ((currentWorkoutItem.excersicesId.length/2)-2)
        }
        holder.itemView.setOnClickListener(View.OnClickListener {
            @Override
            fun onClick(view: View?) {

        } })
    }

    override fun getItemCount(): Int = items.size


}