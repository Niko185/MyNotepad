package com.example.mynotepad.view_model

import androidx.lifecycle.*
import com.example.mynotepad.model.database.MainDataBase
import com.example.mynotepad.entities.NoteItemData
import com.example.mynotepad.entities.ShoppingElementItemData
import com.example.mynotepad.entities.ShoppingListItemData
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException


class MainViewModel(database: MainDataBase) : ViewModel() {
    private val useDao = database.getUseDao()
    val setAllNoteItemData: LiveData<List<NoteItemData>> = useDao.getAllNoteItemData().asLiveData()
    val setAllShoppingLisItemData: LiveData<List<ShoppingListItemData>> = useDao.getAllShoppingListItemData().asLiveData()
    fun setAllShoppingElementItemData(columnId: Int): LiveData<List<ShoppingElementItemData>> {
      return  useDao.getAllShoppingElementItemData(columnId).asLiveData()
    }


    // For NoteFragment
    fun insertNoteItemData(noteItemData: NoteItemData) = viewModelScope.launch {
        useDao.insertNoteItemData(noteItemData)
    }
    fun deleteNoteItemData(columnIdNumberNote: Int ) = viewModelScope.launch {
        useDao.deleteNoteItemData(columnIdNumberNote)
    }
    fun updateNoteItemData(noteItemData: NoteItemData) = viewModelScope.launch {
        useDao.updateNoteItemData(noteItemData)
    }


    //For ShoppingListFragment
    fun insertShoppingListItemData(shoppingListItemData: ShoppingListItemData) = viewModelScope.launch {
        useDao.insertShoppingListItemData(shoppingListItemData)
    }

    fun deleteShoppingListItemData(columnIdNumberShoppingList: Int, deleteList: Boolean) = viewModelScope.launch {
        if(deleteList) useDao.deleteShoppingListItemData(columnIdNumberShoppingList)
            useDao.deleteShoppingElementItemData(columnIdNumberShoppingList)
    }


    fun updateShoppingListItemData(shoppingListItemData: ShoppingListItemData) = viewModelScope.launch {
        useDao.updateShoppingListItemData(shoppingListItemData)
    }

    //For ShoppingElementActivity
    fun insertShoppingElementItemData(shoppingElementItemData: ShoppingElementItemData) = viewModelScope.launch{
        useDao.insertShoppingElementItemData(shoppingElementItemData)
    }

    fun updateShoppingElementItemData(shoppingElementItemData: ShoppingElementItemData) = viewModelScope.launch {
        useDao.updateShoppingElementItemData(shoppingElementItemData)
    }
        /*
    fun deleteShoppingElementItemData(columnId: Int) = viewModelScope.launch {
        useDao.deleteShoppingElementItemData(columnId)
    }
*/

    class MainViewModelFactory(val database: MainDataBase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(MainViewModel::class.java)){
                @Suppress ("UNCHECKED_CAST")
                return MainViewModel(database) as T
            }
            throw IllegalArgumentException("Unknown ViewModelClass")

        }
    }

}
