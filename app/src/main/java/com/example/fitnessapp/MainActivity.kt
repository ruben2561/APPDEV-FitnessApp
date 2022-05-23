package com.example.fitnessapp

/**
 * sources:
 * https://www.youtube.com/watch?v=NENEcwihMCo
 *
 *
 *
 */

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.example.fitnessapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private final var drawer: DrawerLayout? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            R.id.nav_excercises -> supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                ExcercisesFragment()
            ).commit()
            R.id.nav_new_progress_picture -> {
                val intent = Intent(this, NewProgressPictureFragment::class.java)
                startActivity(intent)
            }
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