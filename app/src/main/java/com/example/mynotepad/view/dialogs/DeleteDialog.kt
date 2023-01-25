package com.example.mynotepad.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.mynotepad.databinding.DialogDeleteBinding


object DeleteDialog {

    fun showDialog(context: Context, listenerAction: ListenerAction){
        val binding = DialogDeleteBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(context)
        var mainDeleteDialogInstance: AlertDialog? = null

        builder.setView(binding.root)
        binding.apply {
            tvButtonExitDialog.setOnClickListener{
                mainDeleteDialogInstance?.dismiss()
            }
            tvButtonDeleteDialog.setOnClickListener {
                listenerAction.onClickOpenDialog()
                mainDeleteDialogInstance?.dismiss()
            }
        }

        mainDeleteDialogInstance = builder.create()
        mainDeleteDialogInstance.window?.setBackgroundDrawable(null)
        mainDeleteDialogInstance.show()
    }

    interface ListenerAction {
        fun onClickOpenDialog()
    }

}