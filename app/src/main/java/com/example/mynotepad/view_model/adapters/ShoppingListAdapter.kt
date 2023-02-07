package com.example.mynotepad.adapters

import android.content.Context
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColor
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotepad.R
import com.example.mynotepad.databinding.ItemForFragmentShoppingListBinding

import com.example.mynotepad.entities.ShoppingListItemData


class ShoppingListAdapter(private val listenerItem: ListenerItem) : ListAdapter<ShoppingListItemData, ShoppingListAdapter.ItemHolder>(ItemComparator()) {
    lateinit var bindingItem: ItemForFragmentShoppingListBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        return holder.setData(getItem(position), listenerItem)
    }

    class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val bindingItem = ItemForFragmentShoppingListBinding.bind(view)

        fun setData(shoppingListItemData: ShoppingListItemData, listenerItem: ListenerItem) = with(bindingItem) {

            tvNameList.text = shoppingListItemData.columnName
            //tvTimeList.text = shoppingListItemData.columnTime


            itemView.setOnClickListener {
                listenerItem.sendShoppingListItemDataForShoppingElementActivityAdapterFun(shoppingListItemData)
            }
            tvButtonDelete.setOnClickListener {
                listenerItem.deleteShoppingListItemDataAdapterFun(shoppingListItemData.primaryKey!!)
            }
            tvButtonEdit.setOnClickListener{
                listenerItem.updateShoppingListItemDataAdapterFun(shoppingListItemData)
            }

            val colorState = ColorStateList.valueOf(progressBarColor(shoppingListItemData, bindingItem.root.context))
            progressBarList.max = shoppingListItemData.columnElementInList
            progressBarList.progress = shoppingListItemData.columnCheckedItemCounter
            progressBarList.progressTintList = colorState


           // cardCounter.backgroundTintList = colorState
            val counterText = "${shoppingListItemData.columnCheckedItemCounter} / ${shoppingListItemData.columnElementInList}"
            tvCounterList.text = counterText
        }

        private fun progressBarColor(shoppingListItemData: ShoppingListItemData, context: Context): Int {

            return if(shoppingListItemData.columnCheckedItemCounter == shoppingListItemData.columnElementInList) {
                ContextCompat.getColor(context, R.color.material_A100_light)
            } else ContextCompat.getColor(context, R.color.material_A100_light)
        }


        companion object {
            fun create(parent: ViewGroup): ItemHolder {
                return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_for_fragment_shopping_list, parent, false))
            }
        }

    }



    class ItemComparator : DiffUtil.ItemCallback<ShoppingListItemData>() {
        override fun areItemsTheSame(oldItem: ShoppingListItemData, newItem: ShoppingListItemData): Boolean {
            return oldItem.primaryKey == newItem.primaryKey
        }

        override fun areContentsTheSame(oldItem: ShoppingListItemData, newItem: ShoppingListItemData): Boolean {
            return oldItem == newItem
        }

    }

    interface ListenerItem {
        fun deleteShoppingListItemDataAdapterFun(primaryKey: Int)
        fun updateShoppingListItemDataAdapterFun(shoppingListItemData: ShoppingListItemData)
        fun sendShoppingListItemDataForShoppingElementActivityAdapterFun(shoppingListItemData: ShoppingListItemData)
    }

}