package com.example.mynotepad.view_model.adapters

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
import com.example.mynotepad.entities.ShoppingElementItemData

class ShoppingElementAdapter(private val listenerOnClickActionItem: ListenerOnClickActionItem) : ListAdapter<ShoppingElementItemData, ShoppingElementAdapter.ItemHolder>(ItemComparator()) {
    lateinit var bindingItemElement: ItemForActivityShoppingElementBinding
    // open later lateinit var bindingItemLibrary: ItemLibraryForShoppingElementBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return if (viewType == 0) {
            ItemHolder.inflateElementItem(parent)
        } else
            ItemHolder.inflateElementItem(parent) // change later on Library
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        if(getItem(position).columnType == 0)
            holder.setDataForShoppingElementItem(getItem(position), listenerOnClickActionItem)
        else return // open later holder.setDataForShoppingElementItem(getItem(position))
           // open later holder.setDataForLibraryItem(getItem(position), listenerOnClickActionItem)
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).columnType
    }

        // Подготавливаем данные для item, чтобы потом заполнить данными, эти самые item, в функции выше onBindViewHolder(заполнить item)
    class ItemHolder(view: View): RecyclerView.ViewHolder(view) {
                private val bindingItemElement = ItemForActivityShoppingElementBinding.bind(view)
            // open later - private val bindingItemLibrary = ItemLibraryForShoppingElementBinding.bind(view)

        fun setDataForShoppingElementItem(shoppingElementItemData: ShoppingElementItemData, listenerOnClickActionItem: ListenerOnClickActionItem ) = with(bindingItemElement)  {
                nameElement.text = shoppingElementItemData.columnName
                textSmallElement.text = shoppingElementItemData.columnContent
                textSmallElement.visibility = visibilityChecked(shoppingElementItemData)
                checkBoxElement.isChecked = shoppingElementItemData.columnChecked
                setPaintFlagAndColor()
                checkBoxElement.setOnClickListener {
                    listenerOnClickActionItem.onClickAllItem(shoppingElementItemData.copy(columnChecked = checkBoxElement.isChecked), CHEK_BOX_ICON_PRESSED)
                }
                imageButtonEditElement.setOnClickListener {
                    listenerOnClickActionItem.onClickAllItem(shoppingElementItemData, EDIT_ICON_PRESSED)
                }
        }

            /* Open Later
        fun setDataForLibraryItem(shoppingElementItemDataForLibrary: ShoppingElementItemData, listenerOnClickActionItem: ListenerOnClickActionItem) = with(bindingItemLibrary) {

        }
            */

            private fun setPaintFlagAndColor() = with(bindingItemElement) {
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
            /* open later
            fun inflateLibraryItem(parent: ViewGroup): ItemHolder {
                return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_library_for_shopping_element, parent, false))

            }
            */

        }
    }

    class ItemComparator: DiffUtil.ItemCallback<ShoppingElementItemData>() {
        override fun areItemsTheSame(oldItem: ShoppingElementItemData, newItem: ShoppingElementItemData): Boolean {
            return oldItem.columnIdNumberShoppingElement == newItem.columnIdNumberShoppingElement
        }

        override fun areContentsTheSame(oldItem: ShoppingElementItemData, newItem: ShoppingElementItemData): Boolean {
            return oldItem == newItem
        }
    }





    companion object {
        const val EDIT_ICON_PRESSED = 0
        const val CHEK_BOX_ICON_PRESSED = 1
    }

    interface ListenerOnClickActionItem {
        fun onClickAllItem(shoppingElementItemData: ShoppingElementItemData, stateConstant: Int)

    }



}