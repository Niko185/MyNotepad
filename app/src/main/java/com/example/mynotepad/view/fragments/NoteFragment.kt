package com.example.mynotepad.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynotepad.model.database.MainDataBaseInstance
import com.example.mynotepad.activities.NoteRedactorActivity
import com.example.mynotepad.view_model.MainViewModel
import com.example.mynotepad.adapters.NoteAdapter
import com.example.mynotepad.databinding.FragmentNoteBinding
import com.example.mynotepad.entities.NoteItemData
import com.example.mynotepad.fragments_operation.OptionalFragments


class NoteFragment : OptionalFragments(), NoteAdapter.ListenerOnClickItemNoteFragment {
    private lateinit var binding: FragmentNoteBinding
    private lateinit var intentLauncherInstanceFromNoteFragment: ActivityResultLauncher<Intent>
    private lateinit var noteAdapter: NoteAdapter
    private val mainViewModel: MainViewModel by activityViewModels() {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainDataBaseInstance).mainDataBaseInstance)
    }

    // Fragments Functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launcherIdentificationAction()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerViewNoteFragment()
        observerNoteFragment()

    }

    //Adapter and RcView Functions
    private fun initRecyclerViewNoteFragment() = with(binding) {
        recyclerViewNote.layoutManager = LinearLayoutManager(activity)
        noteAdapter = NoteAdapter(this@NoteFragment)
        recyclerViewNote.adapter = noteAdapter
    }
    private fun observerNoteFragment() {
        mainViewModel.setAllNoteItemData.observe(viewLifecycleOwner) {
            noteAdapter.submitList(it)
        }

    }


    // Launcher Functions NoteFragment <-> NoteRedactorActivity
    override fun onClickAdd() {
        intentLauncherInstanceFromNoteFragment.launch(Intent(activity, NoteRedactorActivity::class.java))
    }

    private fun launcherIdentificationAction() {
        intentLauncherInstanceFromNoteFragment = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val editIdentification = it.data?.getSerializableExtra(KEY_FOR_EDIT_NOTE_ITEM)
                if(editIdentification == "key_edit_identification") {
                    mainViewModel.updateNoteItemData(it.data?.getSerializableExtra(KEY_FOR_CREATE_NEW_NOTE_ITEM) as NoteItemData)
                } else {
                    mainViewModel.insertNoteItemData(it.data?.getSerializableExtra(KEY_FOR_CREATE_NEW_NOTE_ITEM) as NoteItemData)
                }

            }
        }
    }


    override fun deleteNoteItemData(columnIdNumberNote: Int) {
        mainViewModel.deleteNoteItemData(columnIdNumberNote)
    }

    override fun sendNoteItemDataForNoteRedactorActivity(noteItemData: NoteItemData) {
        val intent = Intent(activity, NoteRedactorActivity::class.java).apply {
            putExtra(KEY_FOR_CREATE_NEW_NOTE_ITEM, noteItemData)
        }
        intentLauncherInstanceFromNoteFragment.launch(intent)
    }

    companion object {
        const val KEY_FOR_CREATE_NEW_NOTE_ITEM = "key_for_create_new_note_item"
        const val KEY_FOR_EDIT_NOTE_ITEM = "key_for_edit_note_item"

        @JvmStatic
        fun newInstance() = NoteFragment()
    }
}