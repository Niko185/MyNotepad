package com.example.mynotepad.utils.share

import android.content.Intent
import com.example.mynotepad.entities.ShoppingElementItemData


object ShareHelper {
    fun shareShoppingList(shoppingElement: List<ShoppingElementItemData>, listName: String): Intent {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plane"
        intent.apply {
            putExtra(Intent.EXTRA_TEXT, makeShareText(shoppingElement, listName))
        }
        return intent
    }

    private fun makeShareText(shoppingElementItem: List<ShoppingElementItemData>, listName: String) : String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("<<$listName>>")
        stringBuilder.append("\n")
        var counter = 0
        shoppingElementItem.forEach {
            stringBuilder.append("${++counter} - ${it.columnName} (${it.columnContent})")
            stringBuilder.append("\n")
        }
        return stringBuilder.toString()
    }

}
