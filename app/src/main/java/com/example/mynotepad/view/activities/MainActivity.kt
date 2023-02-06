package com.example.mynotepad.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import com.example.mynotepad.R
import com.example.mynotepad.databinding.ActivityMainBinding
import com.example.mynotepad.fragments_operation.FragmentManager
import com.example.mynotepad.fragments.NoteFragment
import com.example.mynotepad.fragments.ShoppingListFragment
import com.example.mynotepad.view.activities.SettingsActivity

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var currentMenuItemId = R.id.shopping_list
    private lateinit var pref: SharedPreferences
    private var currentTheme = ""

    // Activity functions
    override fun onCreate(savedInstanceState: Bundle?) {
        pref = PreferenceManager.getDefaultSharedPreferences(this)
        currentTheme = pref.getString("key_theme", "Standard theme memory").toString()
        setTheme(getSelectedTheme())
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        launcherFragment()
        setContentView(binding.root)
        setBottomNavigationMenuListener()
    }

    // Bottom menu functions
    private fun setBottomNavigationMenuListener() {
        binding.bottomNavigationMenu.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.settings -> startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
                R.id.note -> {
                    currentMenuItemId = R.id.note
                    FragmentManager.setFragment(NoteFragment.newInstance(), this@MainActivity)
                }
                R.id.shopping_list -> {
                    currentMenuItemId = R.id.shopping_list
                    FragmentManager.setFragment(ShoppingListFragment.newInstance(), this@MainActivity)
                }
                R.id.add_item -> FragmentManager.openNowFragment?.onClickAdd()
            }
            true
        }

    }


    override fun onResume() {
        super.onResume()
        binding.bottomNavigationMenu.selectedItemId = currentMenuItemId
        if(pref.getString("key_theme", "Standard theme memory") != currentTheme) recreate()
    }

    private fun launcherFragment() {
        FragmentManager.setFragment(ShoppingListFragment.newInstance(), this@MainActivity)
        binding.bottomNavigationMenu.selectedItemId = R.id.shopping_list
    }

    private fun getSelectedTheme(): Int {
        return if(pref.getString("key_theme", "Standard theme memory") == "Standard theme memory") R.style.Theme_MyNotepad else R.style.Theme_MyNotepadTwo
    }
}