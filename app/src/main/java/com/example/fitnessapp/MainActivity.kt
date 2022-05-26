package com.example.fitnessapp

/**
 * sources:
 * making recyclerview clickable: https://www.youtube.com/watch?v=NENEcwihMCo
 * sharing data: https://developer.android.com/training/sharing/send
 *
 *
 *
 */

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.room.Room
import com.example.fitnessapp.exercises.ExercisesFragment
import com.example.fitnessapp.Workouts.allWorkouts.AllWorkoutsFragment
import com.example.fitnessapp.Workouts.customWorkouts.CustomWorkoutsFragment
import com.google.android.material.navigation.NavigationView
import com.example.fitnessapp.databinding.ActivityMainBinding
import com.example.fitnessapp.progress.NewProgressPictureFragment


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private final var drawer: DrawerLayout? = null
    private lateinit var binding: ActivityMainBinding
    lateinit var db: GymBuddyDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Room.databaseBuilder(this, GymBuddyDatabase::class.java, "gymBuddyDatabase").createFromAsset("databases/gymBuddyDatabase.db").allowMainThreadQueries().build()
        val toolbar1 = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar1)
        drawer = findViewById(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar1, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer?.addDrawerListener(toggle)
        toggle.syncState()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                AllWorkoutsFragment()
            ).commit()
            navigationView.setCheckedItem(R.id.nav_all_workouts)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_all_workouts -> supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                AllWorkoutsFragment()
            ).commit()
            R.id.nav_custom_workout -> supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                CustomWorkoutsFragment()
            ).commit()
            R.id.nav_exercises -> supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                ExercisesFragment()
            ).commit()
            R.id.nav_new_progress_picture -> supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                NewProgressPictureFragment()
            ).commit()
            R.id.nav_info -> Toast.makeText(this, "ik ben ruben", Toast.LENGTH_SHORT).show()
        }
        drawer!!.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
            drawer!!.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}