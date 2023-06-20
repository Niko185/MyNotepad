package com.example.mynotepad.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotepad.R
import com.example.mynotepad.databinding.ItemForActivityShoppingElementBinding
import com.example.mynotepad.databinding.ItemLibraryForShoppingElementBinding
import com.example.mynotepad.entities.ShoppingElementItemData

class ShoppingElementAdapter(private val listenerScreen: ListenerScreen) : ListAdapter<ShoppingElementItemData, ShoppingElementAdapter.ItemHolder>(ItemComparator()) {
    lateinit var bindingItemElement: ItemForActivityShoppingElementBinding
    lateinit var bindingItemLibrary: ItemLibraryForShoppingElementBinding

    override fun getItemViewType(position: Int): Int {
        return getItem(position).columnType
    }

    class ItemHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun setDataShoppingElementItem(
            shoppingElementItemData: ShoppingElementItemData,
            listenerScreen: ListenerScreen
        ) {
            val bindingItemElement = ItemForActivityShoppingElementBinding.bind(view)
            bindingItemElement.nameElement.text = shoppingElementItemData.columnName
            bindingItemElement.textSmallElement.text = shoppingElementItemData.columnContent
            bindingItemElement.textSmallElement.visibility =
                visibilityChecked(shoppingElementItemData)
            bindingItemElement.checkBoxElement.isChecked = shoppingElementItemData.columnChecked
            setPaintFlagAndColor(bindingItemElement)
            bindingItemElement.checkBoxElement.setOnClickListener {
                listenerScreen.onClickAllItemAdapterFun(
                    shoppingElementItemData.copy(columnChecked = bindingItemElement.checkBoxElement.isChecked),
                    CHEK_BOX_ICON_PRESSED
                )
            }
            bindingItemElement.imageButtonEditElement.setOnClickListener {
                listenerScreen.onClickAllItemAdapterFun(shoppingElementItemData, EDIT_ICON_PRESSED)
            }

            bindingItemElement.imageButtonClose.setOnClickListener {
                listenerScreen.deleteItemAdapterFun(shoppingElementItemData.primaryKey!!)
            }
        }

        fun setDataLibraryItem(shoppingElementItemData: ShoppingElementItemData, listenerScreen: ListenerScreen) {
             val bindingLibraryElement = ItemLibraryForShoppingElementBinding.bind(view)

            bindingLibraryElement.tvTitleLibraryItem.text = shoppingElementItemData.columnName
            bindingLibraryElement.imEditLibraryItem.setOnClickListener {
                listenerScreen.onClickAllItemAdapterFun(shoppingElementItemData, EDIT_ICON_LIBRARY_ITEM_PRESSED)
            }
            bindingLibraryElement.imDeleteLibraryItem.setOnClickListener {
                listenerScreen.onClickAllItemAdapterFun(shoppingElementItemData, DELETE_ICON_LIBRARY_ITEM_PRESSED)
            }

            itemView.setOnClickListener {
                listenerScreen.onClickAllItemAdapterFun(shoppingElementItemData, ADD_ICON_LIBRARY_ITEM)
            }
        }

            private fun setPaintFlagAndColor(bindingItemElement: ItemForActivityShoppingElementBinding) = with(bindingItemElement) {
                if (checkBoxElement.isChecked) {
                    nameElement.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    textSmallElement.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    nameElement.setTextColor(ContextCompat.getColor(bindingItemElement.root.context, R.color.paint_flag_text_color_grey))
                    textSmallElement.setTextColor(ContextCompat.getColor(bindingItemElement.root.context, R.color.paint_flag_text_color_grey))
                } else {
                    nameElement.paintFlags = Paint.ANTI_ALIAS_FLAG
                    textSmallElement.paintFlags = Paint.ANTI_ALIAS_FLAG
                    nameElement.setTextColor(ContextCompat.getColor(bindingItemElement.root.context, R.color.classic_text_classic_grey))
                    textSmallElement.setTextColor(ContextCompat.getColor(bindingItemElement.root.context, R.color.classic_text_classic_grey))
                }
            }

            private fun visibilityChecked(shoppingElementItemData: ShoppingElementItemData) : Int {
                 return if(shoppingElementItemData.columnContent.isNullOrEmpty()) {
                     View.GONE
                 } else View.VISIBLE
            }

        companion object {
                fun inflateElementItem(parent: ViewGroup): ItemHolder {
                return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_for_activity_shopping_element, parent, false))
            }
                fun inflateLibraryItem(parent: ViewGroup): ItemHolder {
                return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_library_for_shopping_element, parent, false))
            }
        }
    }

    class ItemComparator: DiffUtil.ItemCallback<ShoppingElementItemData>() {
        override fun areItemsTheSame(oldItem: ShoppingElementItemData, newItem: ShoppingElementItemData): Boolean {
            return oldItem.primaryKey == newItem.primaryKey
        }

        override fun areContentsTheSame(oldItem: ShoppingElementItemData, newItem: ShoppingElementItemData): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return if (viewType == 0) {
            ItemHolder.inflateElementItem(parent)
        } else
            ItemHolder.inflateLibraryItem(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        if (getItem(position).columnType == 0)
            holder.setDataShoppingElementItem(getItem(position), listenerScreen)
        else  holder.setDataLibraryItem(getItem(position), listenerScreen)

    }

    companion object {
        const val EDIT_ICON_PRESSED = 0
        const val CHEK_BOX_ICON_PRESSED = 1
        const val EDIT_ICON_LIBRARY_ITEM_PRESSED = 2
        const val DELETE_ICON_LIBRARY_ITEM_PRESSED = 3
        const val ADD_ICON_LIBRARY_ITEM = 4
    }

    interface ListenerScreen {
        fun onClickAllItemAdapterFun(shoppingElementItemData: ShoppingElementItemData, stateConstant: Int)
        fun deleteItemAdapterFun(primaryKey: Int)
    }



}