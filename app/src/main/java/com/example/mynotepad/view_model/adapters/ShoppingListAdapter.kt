package com.example.mynotepad.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotepad.R
import com.example.mynotepad.databinding.ItemForFragmentShoppingListBinding

import com.example.mynotepad.entities.ShoppingListItemData


class ShoppingListAdapter(private val listenerOnClickItemShoppingListFragment: ListenerOnClickItemShoppingListFragment) : ListAdapter<ShoppingListItemData, ShoppingListAdapter.ItemHolder>(ItemComparator()) {
    lateinit var bindingItem: ItemForFragmentShoppingListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        return holder.setData(getItem(position), listenerOnClickItemShoppingListFragment)
    }

    class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val bindingItem = ItemForFragmentShoppingListBinding.bind(view)

        fun setData(shoppingListItemData: ShoppingListItemData, listenerOnClickItemItemShoppingListFragment: ListenerOnClickItemShoppingListFragment) = with(bindingItem) {

            tvNameList.text = shoppingListItemData.columnName
            tvTimeList.text = shoppingListItemData.columnTime


            itemView.setOnClickListener {
                listenerOnClickItemItemShoppingListFragment.sendShoppingListItemDataForShoppingElementActivityAdapterFun(shoppingListItemData)
            }
            tvButtonDelete.setOnClickListener {
                listenerOnClickItemItemShoppingListFragment.deleteShoppingListItemDataAdapterFun(shoppingListItemData.columnIdNumberShoppingList!!)
            }
            tvButtonEdit.setOnClickListener{
                listenerOnClickItemItemShoppingListFragment.updateShoppingListItemDataAdapterFun(shoppingListItemData)
            }

        }


        companion object {
            fun create(parent: ViewGroup): ItemHolder {
                return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_for_fragment_shopping_list, parent, false))
            }
        }

    }



    class ItemComparator : DiffUtil.ItemCallback<ShoppingListItemData>() {
        override fun areItemsTheSame(oldItem: ShoppingListItemData, newItem: ShoppingListItemData): Boolean {
            return oldItem.columnIdNumberShoppingList == newItem.columnIdNumberShoppingList
        }

        override fun areContentsTheSame(oldItem: ShoppingListItemData, newItem: ShoppingListItemData): Boolean {
            return oldItem == newItem
        }

    }

    interface ListenerOnClickItemShoppingListFragment {
        fun deleteShoppingListItemDataAdapterFun(columnIdNumberShoppingList: Int)
        fun updateShoppingListItemDataAdapterFun(shoppingListItemData: ShoppingListItemData)
        fun sendShoppingListItemDataForShoppingElementActivityAdapterFun(shoppingListItemData: ShoppingListItemData)
    }

}