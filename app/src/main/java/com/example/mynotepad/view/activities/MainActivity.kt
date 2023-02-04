package com.example.mynotepad.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mynotepad.R
import com.example.mynotepad.databinding.ActivityMainBinding
import com.example.mynotepad.fragments_operation.FragmentManager
import com.example.mynotepad.fragments.NoteFragment
import com.example.mynotepad.fragments.ShoppingListFragment
import com.example.mynotepad.view.activities.SettingsActivity

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    // Activity functions
    override fun onCreate(savedInstanceState: Bundle?) {
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
                R.id.note -> FragmentManager.setFragment(NoteFragment.newInstance(), this@MainActivity)
                R.id.shopping_list -> FragmentManager.setFragment(ShoppingListFragment.newInstance(), this@MainActivity)
                R.id.add_item -> FragmentManager.openNowFragment?.onClickAdd()
            }
            true
        }

    }




    private fun launcherFragment() {
        FragmentManager.setFragment(ShoppingListFragment.newInstance(), this@MainActivity)
        binding.bottomNavigationMenu.selectedItemId = R.id.shopping_list
    }
}