package com.example.mynotepad.view_model

import androidx.lifecycle.*
import com.example.mynotepad.entities.LibraryItemData
import com.example.mynotepad.model.database.MainDataBase
import com.example.mynotepad.entities.NoteItemData
import com.example.mynotepad.entities.ShoppingElementItemData
import com.example.mynotepad.entities.ShoppingListItemData
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException


class MainViewModel(database: MainDataBase) : ViewModel() {
    private val useDao = database.getUseDao()

    // For NoteFragment
    val setAllNoteItemData: LiveData<List<NoteItemData>> = useDao.getAllNoteItemData().asLiveData()

    fun insertNoteItemData(noteItemData: NoteItemData) = viewModelScope.launch {
        useDao.insertNoteItemData(noteItemData)
    }

    fun deleteNoteItemData(primaryKey: Int ) = viewModelScope.launch {
        useDao.deleteNoteItemData(primaryKey)
    }

    fun updateNoteItemData(noteItemData: NoteItemData) = viewModelScope.launch {
        useDao.updateNoteItemData(noteItemData)
    }


    //For ShoppingListFragment
    val setAllShoppingLisItemData: LiveData<List<ShoppingListItemData>> = useDao.getAllShoppingListItemData().asLiveData()

    fun insertShoppingListItemData(shoppingListItemData: ShoppingListItemData) = viewModelScope.launch {
        useDao.insertShoppingListItemData(shoppingListItemData)
    }

    fun deleteShoppingListItemData(primaryKey: Int) = viewModelScope.launch {
         useDao.deleteShoppingListItemData(primaryKey)

    }

    fun updateShoppingListItemData(shoppingListItemData: ShoppingListItemData) = viewModelScope.launch {
        useDao.updateShoppingListItemData(shoppingListItemData)
    }


    //For ShoppingElementActivity & Library
    fun setAllShoppingElementItemData(columnId: Int): LiveData<List<ShoppingElementItemData>> {
        return  useDao.getAllShoppingElementItemData(columnId).asLiveData()
    }
    fun deleteShoppingElementItemData(primaryKey: Int) = viewModelScope.launch{
        useDao.deleteShoppingElementItemData(primaryKey)
    }

    fun insertShoppingElementItemData(shoppingElementItemData: ShoppingElementItemData) = viewModelScope.launch{
        useDao.insertShoppingElementItemData(shoppingElementItemData)
    }

    fun clearShoppingElementItemData(primaryKey: Int) = viewModelScope.launch {
        useDao.clearShoppingElementItemData(primaryKey)
    }

    fun updateShoppingElementItemData(shoppingElementItemData: ShoppingElementItemData) = viewModelScope.launch {
        useDao.updateShoppingElementItemData(shoppingElementItemData)
    }

    fun insertLibraryData(libraryItemData:  LibraryItemData) = viewModelScope.launch {
        useDao.insertLibraryItemData(libraryItemData)
    }

    private suspend fun libraryItemCheck(columnName: String): Boolean {
        return useDao.getAllLibraryItemData(columnName).isNotEmpty()
    }



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
