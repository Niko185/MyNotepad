package com.example.mynotepad.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.mynotepad.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preference, rootKey)
    }
}