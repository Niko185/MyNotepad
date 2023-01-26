package com.example.mynotepad.adapters

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


class NoteAdapter(private val listenerOnClickItemNoteFragment: ListenerOnClickItemNoteFragment) : ListAdapter<NoteItemData, NoteAdapter.ItemHolder>(ItemComparator()) {
    lateinit var bindingItem: ItemForFragmentNoteBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        return holder.setData(getItem(position), listenerOnClickItemNoteFragment)
    }



    class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val bindingItem = ItemForFragmentNoteBinding.bind(view)

        fun setData(noteItemData: NoteItemData, listenerOnClickItemNoteFragment: ListenerOnClickItemNoteFragment) = with(bindingItem) {
                nameItemNoteFragment.text = noteItemData.columnName
                descriptionItemNoteFragment.text = HtmlManager.getTextFromHtml(noteItemData.columnDescription).trim()
                timeItemNoteFragment.text = noteItemData.columnTime

                itemView.setOnClickListener {
                    listenerOnClickItemNoteFragment.sendNoteItemDataForNoteRedactorActivity(noteItemData)
                }
                deleteItemNoteFragment.setOnClickListener{
                    listenerOnClickItemNoteFragment.deleteNoteItemFromRcView(noteItemData.columnIdNumberNote!!)
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
            return oldItem.columnIdNumberNote == newItem.columnIdNumberNote
        }

        override fun areContentsTheSame(oldItem: NoteItemData, newItem: NoteItemData): Boolean {
            return oldItem == newItem
        }

    }


    interface ListenerOnClickItemNoteFragment {
        fun deleteNoteItemFromRcView(columnIdNumberNote: Int)
        fun sendNoteItemDataForNoteRedactorActivity(noteItemData: NoteItemData)
    }

}