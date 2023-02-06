package com.example.mynotepad.adapters

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotepad.R
import com.example.mynotepad.databinding.ItemForFragmentNoteBinding
import com.example.mynotepad.entities.NoteItemData
import com.example.mynotepad.utils.HtmlManager
import com.example.mynotepad.utils.TimeManager


class NoteAdapter(private val listenerOnClickItemNoteFragment: ListenerOnClickItemNoteFragment, private val pref: SharedPreferences) : ListAdapter<NoteItemData, NoteAdapter.ItemHolder>(ItemComparator()) {
    lateinit var bindingItem: ItemForFragmentNoteBinding



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        return holder.setData(getItem(position), listenerOnClickItemNoteFragment, pref)
    }



    class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val bindingItem = ItemForFragmentNoteBinding.bind(view)

        fun setData(noteItemData: NoteItemData, listenerOnClickItemNoteFragment: ListenerOnClickItemNoteFragment, pref: SharedPreferences) = with(bindingItem) {
                nameItemNoteFragment.text = noteItemData.columnName
                descriptionItemNoteFragment.text = HtmlManager.getTextFromHtml(noteItemData.columnDescription).trim()
                timeItemNoteFragment.text = TimeManager.getTimeFormat(noteItemData.columnTime, pref)

                itemView.setOnClickListener {
                    listenerOnClickItemNoteFragment.sendNoteItemDataForNoteRedactorActivity(noteItemData)
                }
                deleteItemNoteFragment.setOnClickListener{
                    listenerOnClickItemNoteFragment.deleteNoteItemFromRcView(noteItemData.primaryKey!!)
                }
        }


        companion object {
        fun create(parent: ViewGroup): ItemHolder {
            return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_for_fragment_note, parent, false))
            }
        }

    }



    class ItemComparator : DiffUtil.ItemCallback<NoteItemData>() {
        override fun areItemsTheSame(oldItem: NoteItemData, newItem: NoteItemData): Boolean {
            return oldItem.primaryKey == newItem.primaryKey
        }

        override fun areContentsTheSame(oldItem: NoteItemData, newItem: NoteItemData): Boolean {
            return oldItem == newItem
        }

    }


    interface ListenerOnClickItemNoteFragment {
        fun deleteNoteItemFromRcView(primaryKey: Int)
        fun sendNoteItemDataForNoteRedactorActivity(noteItemData: NoteItemData)
    }

}