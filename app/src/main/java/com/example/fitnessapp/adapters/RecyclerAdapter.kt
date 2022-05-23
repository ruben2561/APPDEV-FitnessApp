package com.example.fitnessapp.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.R
import com.example.fitnessapp.databaseExcercises.Excercise


class RecyclerAdapter(private var items: List<Excercise>, private var onItemClickListener: OnItemClickListener):
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.excercise, parent, false)
        return ViewHolder(view, onItemClickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val excercise = items[position]
        holder.bind(excercise)
    }

    class ViewHolder(itemView: View, onItemClickListener: OnItemClickListener): RecyclerView.ViewHolder(itemView){

        init{
            itemView.setOnClickListener{
                //val snack: String = "item position clicked: $adapterPosition"
                //Snackbar.make(itemView, snack, Snackbar.LENGTH_SHORT).show()
                onItemClickListener.OnClick(adapterPosition)
            }
        }

        fun bind(excercise: Excercise){
            itemView.apply{findViewById<TextView>(R.id.txtExcerciseName).text = excercise.name}
            itemView.apply{findViewById<TextView>(R.id.txtMuscleGroup).text = excercise.muscleGroup}
        }
    }

    interface OnItemClickListener{
        fun OnClick(position: Int)
    }


}
