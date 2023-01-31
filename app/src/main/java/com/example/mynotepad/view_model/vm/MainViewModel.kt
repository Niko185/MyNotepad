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


    //For ShoppingElementActivity
    fun setAllShoppingElementItemData(columnId: Int): LiveData<List<ShoppingElementItemData>> {
        return  useDao.getAllShoppingElementItemData(columnId).asLiveData()
    }
    fun deleteShoppingElementItemData(primaryKey: Int) = viewModelScope.launch{
        useDao.deleteShoppingElementItemData(primaryKey)
    }

    fun insertShoppingElementAndLibraryItemData(shoppingElementItemData: ShoppingElementItemData) = viewModelScope.launch{
        useDao.insertShoppingElementItemData(shoppingElementItemData)
        if(!isNameNotHereLibrary(shoppingElementItemData.columnName)) useDao.insertLibraryItemData(LibraryItemData(null, shoppingElementItemData.columnName))
    }

    fun clearShoppingElementItemData(primaryKey: Int) = viewModelScope.launch {
        useDao.clearShoppingElementItemData(primaryKey)
    }

    fun updateShoppingElementItemData(shoppingElementItemData: ShoppingElementItemData) = viewModelScope.launch {
        useDao.updateShoppingElementItemData(shoppingElementItemData)
    }

    // For Library
    var libraryItems = MutableLiveData<List<LibraryItemData>>()

    fun getAllLibraryItemData(columnName: String) = viewModelScope.launch {
    libraryItems.postValue(useDao.getAllLibraryItemData(columnName))
    }

    fun updateLibraryItemData(libraryItemData: LibraryItemData) = viewModelScope.launch {
        useDao.updateLibraryItemData(libraryItemData)
    }

    fun deleteLibraryItemData(primaryKey: Int) = viewModelScope.launch {
        useDao.deleteLibraryItemData(primaryKey)
    }

    private suspend fun isNameNotHereLibrary(columnName: String): Boolean {
        return useDao.getAllLibraryItemData(columnName).isNotEmpty()
    }



     // ViewModelFactory
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
