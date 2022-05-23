package com.example.fitnessapp

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.fitnessapp.databaseExcercises.Excercise
import com.example.fitnessapp.databaseExcercises.ExcerciseDatabase
import com.example.fitnessapp.adapters.RecyclerAdapter
import com.example.fitnessapp.databaseWorkouts.Workout
import com.example.fitnessapp.databaseWorkouts.WorkoutDatabase
import com.example.fitnessapp.databinding.FragmentCustomWorkoutsNewWorkoutBinding

class NewWorkoutFragment : Fragment(), RecyclerAdapter.OnItemClickListener{

    private lateinit var binding: FragmentCustomWorkoutsNewWorkoutBinding

    var choisesList = ArrayList<Excercise>()
    var filteredList = ArrayList<Excercise>()
    lateinit var recyclerList: MutableList<Excercise>
    var toggleState = 1; //0 when all excercises are showen, 1 when the chosen excercises are displayed

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomWorkoutsNewWorkoutBinding.inflate(layoutInflater)
        /*val db = Room.databaseBuilder(requireContext(), ExcerciseDatabase::class.java, "excercisedatabase-db").createFromAsset("databases/excercisedatabase-db.db").allowMainThreadQueries().build()
        val excerciseDao = db.excerciseDao()
        recyclerList = excerciseDao.getAll()
        val recyclerAdapter = RecyclerAdapter(recyclerList, this)
        binding.rvwExcercises.adapter = recyclerAdapter
        binding.rvwExcercises.layoutManager = LinearLayoutManager(context)
        binding.rvwExcercises.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))*/

        binding.addExcercise.setOnClickListener{

            if(toggleState == 0){
                binding.excerciseFilter.visibility = View.INVISIBLE
                recyclerList = choisesList
                binding.rvwExcercises.adapter = RecyclerAdapter(recyclerList, this)
                binding.rvwExcercises.layoutManager = LinearLayoutManager(context)
                toggleState = 1
            }

            else if(toggleState == 1){
                binding.excerciseFilter.visibility = View.VISIBLE
                val db = Room.databaseBuilder(requireContext(), ExcerciseDatabase::class.java, "excercisedatabase-db").createFromAsset("databases/excercisedatabase-db.db").allowMainThreadQueries().build()
                val excerciseDao = db.excerciseDao()                                                                                                           //
                recyclerList = excerciseDao.getAll()
                binding.rvwExcercises.adapter = RecyclerAdapter(recyclerList, this)                                              // adds the excercises list in the recyclerview
                binding.rvwExcercises.layoutManager = LinearLayoutManager(context)                                        // chooses what type of layout
                toggleState = 0
            }

        }

        /**
         * saves workout in workout database if fields arent empty
         */
        binding.saveWorkout.setOnClickListener{
            var db = Room.databaseBuilder(requireContext(), WorkoutDatabase::class.java, "workouts.database").allowMainThreadQueries().build()  //
            var workoutDao = db.workoutDao()
            var name = binding.workoutTitle.text.toString()
            var excercisesIds = ""
            for (item in choisesList){
                excercisesIds = excercisesIds + item.id + ","
            }
            if(!name.contentEquals("") && excercisesIds != ""){
                workoutDao.insertOne(Workout(name, excercisesIds, 0))
                Toast.makeText(this.context, "Workout saved!", Toast.LENGTH_LONG).show()
                choisesList.clear()
            }
            else{
                Toast.makeText(this.context, "enter workout name and excercises", Toast.LENGTH_LONG).show()
            }
        }

        if(toggleState == 1){
            binding.excerciseFilter.addTextChangedListener(object : TextWatcher,
                RecyclerAdapter.OnItemClickListener {                                //this is used to check the typed word which than filters the recyclerview to show to excercises that have a matching name or muscle group
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    filteredList = ArrayList()
                    val db = Room.databaseBuilder(requireContext(), ExcerciseDatabase::class.java, "excercisedatabase-db").createFromAsset("databases/excercisedatabase-db.db").allowMainThreadQueries().build()
                    val excerciseDao = db.excerciseDao()
                    var tempList = excerciseDao.getAll()
                    for (item in tempList) {
                        if (item.name.lowercase().contains(s.toString().lowercase())) {
                            filteredList.add(item)
                        }
                        if (item.muscleGroup.lowercase().contains(s.toString().lowercase())) {
                            filteredList.add(item)
                        }
                    }
                    recyclerList = filteredList
                    binding.rvwExcercises.adapter = RecyclerAdapter(recyclerList, this)
                    binding.rvwExcercises.layoutManager = LinearLayoutManager(context)
                }

                override fun OnClick(position: Int) {
                    var filteredExcercisename = filteredList[position].name
                    val db = Room.databaseBuilder(requireContext(), ExcerciseDatabase::class.java, "excercisedatabase-db").createFromAsset("databases/excercisedatabase-db.db").allowMainThreadQueries().build()
                    val excerciseDao = db.excerciseDao()
                    showAddedExcercise(filteredExcercisename)
                    choisesList.add(excerciseDao.findByName(filteredExcercisename))
                }
            })
        }



        return binding.root
    }

    override fun OnClick(position: Int) {
        val db = Room.databaseBuilder(requireContext(), ExcerciseDatabase::class.java, "excercisedatabase-db").createFromAsset("databases/excercisedatabase-db.db").allowMainThreadQueries().build()
        val excerciseDao = db.excerciseDao()
        showAddedExcercise(excerciseDao.loadByIds(position+1).name)
        choisesList.add(excerciseDao.loadByIds(position+1))
    }

    fun showAddedExcercise(name: String){
        val db = Room.databaseBuilder(requireContext(), ExcerciseDatabase::class.java, "excercisedatabase-db").createFromAsset("databases/excercisedatabase-db.db").allowMainThreadQueries().build()
        val excerciseDao = db.excerciseDao()
        val toast = Toast.makeText(this.context, name + " added to routine", Toast.LENGTH_SHORT)
        toast.show()

        /*
        this makes the toast message fade quicker
        source: https://stackoverflow.com/questions/3775074/set-toast-appear-length/9715422#9715422
         */
        val handler = Handler()
        handler.postDelayed(Runnable { toast.cancel() }, 750)
    }
}