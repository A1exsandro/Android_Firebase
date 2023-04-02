package com.nst.androidfirebase.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.nst.androidfirebase.R
import com.nst.androidfirebase.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = binding.drawerLayout
        val navView : NavigationView = binding.navView

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {

            it.isChecked = true

            when(it.itemId) {

                R.id.nav_home -> replaceFragment(HomeFragment(), it.title.toString())
                R.id.nav_message -> Toast.makeText(applicationContext, "Clicked Home", Toast.LENGTH_SHORT).show()
                R.id.nav_login -> Toast.makeText(applicationContext, "Clicked Home", Toast.LENGTH_SHORT).show()
                R.id.nav_share -> Toast.makeText(applicationContext, "Clicked Home", Toast.LENGTH_SHORT).show()
                R.id.nav_new_words -> replaceFragment(NewWordsFragment(), it.title.toString())
                R.id.nav_known_words -> replaceFragment(KnownWordsFragment(), it.title.toString())
                R.id.nav_all_words -> Toast.makeText(applicationContext, "Clicked Home", Toast.LENGTH_SHORT).show()
            }

            true
        }
    }

    private fun replaceFragment(fragment: Fragment, title: String) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
        setTitle(title)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}

