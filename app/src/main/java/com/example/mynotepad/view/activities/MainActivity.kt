package com.example.mynotepad.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mynotepad.R
import com.example.mynotepad.databinding.ActivityMainBinding
import com.example.mynotepad.fragments_operation.FragmentManager
import com.example.mynotepad.fragments.NoteFragment
import com.example.mynotepad.fragments.ShoppingListFragment

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
                R.id.settings -> Toast.makeText(applicationContext,"Settings click",Toast.LENGTH_SHORT).show()
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