package com.example.mynotepad.fragments_operation

import androidx.appcompat.app.AppCompatActivity
import com.example.mynotepad.R

object FragmentManager {
    var openNowFragment: OptionalFragments? = null

    fun setFragment(newFrag: OptionalFragments, context_activity: AppCompatActivity) {
        val transaction = context_activity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.placeHolder, newFrag)
        transaction.commit()
        openNowFragment = newFrag
    }

}