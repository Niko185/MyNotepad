package com.example.mynotepad.activities

import android.content.SharedPreferences
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.example.mynotepad.R
import com.example.mynotepad.fragments.SettingsFragment

class SettingsActivity : AppCompatActivity() {
    private lateinit var pref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        pref = PreferenceManager.getDefaultSharedPreferences(this)
        val themePref = if(pref.getString("key_theme", "Standard theme memory") == "Standard theme memory") R.style.Theme_MyNotepad  else R.style.Theme_MyNotepadTwo

        if(themePref != R.style.Theme_MyNotepad) {
            setTheme(resources.getIdentifier(themePref.toString(), "style", this.packageName))
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragmentHolder, SettingsFragment()).commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private val listener = SharedPreferences.OnSharedPreferenceChangeListener { pref, key ->
        if (key == "key_theme") {
            recreate()
        }
    }

    override fun onResume() {
        super.onResume()
        pref.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun onPause() {
        pref.unregisterOnSharedPreferenceChangeListener(listener)
        super.onPause()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}


