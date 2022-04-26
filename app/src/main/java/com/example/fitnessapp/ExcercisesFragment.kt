package com.example.fitnessapp

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.fitnessapp.database.Excercise
import com.example.fitnessapp.database.ExcerciseAdapter
import com.example.fitnessapp.database.ExcerciseDao
import com.example.fitnessapp.database.ExcerciseDatabase
import com.example.fitnessapp.databinding.FragmentExcercisesBinding

class ExcercisesFragment : Fragment() {

    private lateinit var binding: FragmentExcercisesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExcercisesBinding.inflate(layoutInflater)
        val db = Room.databaseBuilder(requireContext(), ExcerciseDatabase::class.java, "excercisedatabase4-db").allowMainThreadQueries().build()
        val excerciseDao = db.excerciseDao()
        val excercises: MutableList<Excercise> = excerciseDao.getAll()

        //var adapter = ExcerciseAdapter(excercises)
        binding.rvwExcercises.adapter = ExcerciseAdapter(excercises)
        binding.rvwExcercises.layoutManager = GridLayoutManager(context, 2)
        //binding.rvwExcercises.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))


        /*excerciseDao.insert(listOf(Excercise("Bench Press",    "Chest", 0),
                                   Excercise("Military Press", "Shoulders",0),
                                   Excercise("Bicep Curl",     "Biceps",0),
                                   Excercise("Sit-Ups",        "Abs",0),
                                   Excercise("Tricep Pushdown","Triceps",0),
                                   Excercise("Lat Pulldown",   "Back",0),
                                   Excercise("Leg Press",      "Legs",0),
                                   Excercise("Shoulder Press", "Shoulders", 0),
                                   Excercise("Squat",          "Legs",0),
                                   Excercise("Seated Row",     "Back",0),
                                   Excercise("Plank",          "Abs",0),
                                   Excercise("Pec Fly",        "Chest",0),
                                   Excercise("Back Extention", "Back",0)))*/


        /*binding.buttonTodos.setOnClickListener() {
            todoDao.deleteById(todos.size)
            //todoDao.delete(todoDao.)
            val todos: MutableList<Todo> = todoDao.getAll()
            binding.rvwTodo.adapter = OefAdapter(todos)
            binding.rvwTodo.layoutManager = LinearLayoutManager(this)
            //val separator = " ,"
            //val string1 = todos.joinToString(separator)
            //Toast.makeText(this, string1, Toast.LENGTH_SHORT).show()
        }*/

        /*binding.buttonAdd.setOnClickListener{
            val naam = binding.Textname.getText().toString()
            val newTodo = Todo(naam, false, 0)
            todoDao.insert(listOf(newTodo))
            val todos: MutableList<Todo> = todoDao.getAll()
            binding.rvwTodo.adapter = OefAdapter(todos)
            binding.rvwTodo.layoutManager = LinearLayoutManager(this)
            //binding.rvwTodo.smoothScrollBy(binding.rvwTodo.size-1)
            //todos.add(newTodo)
            //binding.Textname.getText().clear()
            //adapter.notifyDataSetChanged()

        }*/

        return binding.root
    }
}