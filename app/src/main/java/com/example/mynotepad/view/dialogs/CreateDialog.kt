package com.example.mynotepad.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.mynotepad.R
import com.example.mynotepad.databinding.DialogCreateBinding



object CreateDialog {

    fun showDialog(context: Context, listenerAction: ListenerAction, name: String) {
        val binding = DialogCreateBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(context)
        var mainDialogList: AlertDialog? = null

        builder.setView(binding.root)
        binding.apply {

            idEditTextDialog.setText(name)
            if(name.isNotEmpty()) {
                idButtonDialog.text = context.getString(R.string.update)
            } else idButtonDialog.text = context.getString(R.string.button_text_create_variant)

            idButtonDialog.setOnClickListener {
                val listName = idEditTextDialog.text.toString()
                if(listName.isNotEmpty()) {
                    listenerAction.onClickOpenDialog(listName)
                } else {
                    mainDialogList?.dismiss()
                }
                mainDialogList?.onBackPressed()
            }
        }
        mainDialogList = builder.create()
        mainDialogList.window?.setBackgroundDrawable(null)
        mainDialogList.show()
    }



    interface ListenerAction {
        fun onClickOpenDialog(nameList: String)
    }


}
