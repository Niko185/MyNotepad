package com.example.mynotepad.view.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.mynotepad.databinding.DialogEditElementBinding
import com.example.mynotepad.entities.ShoppingElementItemData


object DialogEditElement {

    fun showDialog(context: Context, item: ShoppingElementItemData, listenerAction: ListenerAction) {
        val bindingDialog = DialogEditElementBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(context)
        var mainEditDialogElementInstance: AlertDialog? = null


        builder.setView(bindingDialog.root)
        bindingDialog.apply {
            edName.setText(item.columnName)
            edInfo.setText(item.columnContent)
            bUpdate.setOnClickListener {
                if(edName.text.toString().isNotEmpty()){
                    val itemInfo = if(edInfo.text.isEmpty()) null else edInfo.text.toString()
                    listenerAction.onClickOpenDialog(item.copy(columnName = edName.text.toString(), columnContent = itemInfo))
                }
                mainEditDialogElementInstance?.dismiss()
            }
        }

        mainEditDialogElementInstance = builder.create()
        mainEditDialogElementInstance.window?.setBackgroundDrawable(null)
        mainEditDialogElementInstance.show()
    }

    interface ListenerAction {
        fun onClickOpenDialog(item: ShoppingElementItemData)
    }
}