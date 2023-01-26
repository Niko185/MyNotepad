package com.example.mynotepad.view_model

import androidx.lifecycle.*
import com.example.mynotepad.entities.LibraryData
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

    fun deleteNoteItemData(columnIdNumberNote: Int ) = viewModelScope.launch {
        useDao.deleteNoteItemData(columnIdNumberNote)
    }

    fun updateNoteItemData(noteItemData: NoteItemData) = viewModelScope.launch {
        useDao.updateNoteItemData(noteItemData)
    }


    //For ShoppingListFragment

    val setAllShoppingLisItemData: LiveData<List<ShoppingListItemData>> = useDao.getAllShoppingListItemDataDAOFUN().asLiveData()

    fun insertShoppingListItemDataViewModelFun(shoppingListItemData: ShoppingListItemData) = viewModelScope.launch {
        useDao.insertShoppingListItemDataDAOFUN(shoppingListItemData)
    }

    fun deleteShoppingListItemDataViewModelFun(columnIdNumberShoppingList: Int, deleteList: Boolean) = viewModelScope.launch {
        if(deleteList) useDao.deleteShoppingListItemDataDAOFUN(columnIdNumberShoppingList)
            useDao.deleteShoppingElementItemData(columnIdNumberShoppingList)
    }

    fun updateShoppingListItemDataViewModelFun(shoppingListItemData: ShoppingListItemData) = viewModelScope.launch {
        useDao.updateShoppingListItemDataDAOFUN(shoppingListItemData)
    }


    //For ShoppingElementActivity
    fun setAllShoppingElementItemData(columnId: Int): LiveData<List<ShoppingElementItemData>> {
        return  useDao.getAllShoppingElementItemData(columnId).asLiveData()
    }

    fun insertShoppingElementItemData(shoppingElementItemData: ShoppingElementItemData) = viewModelScope.launch{
        useDao.insertShoppingElementItemData(shoppingElementItemData)
    }

    fun updateShoppingElementItemData(shoppingElementItemData: ShoppingElementItemData) = viewModelScope.launch {
        useDao.updateShoppingElementItemData(shoppingElementItemData)
    }

    // For ShoppingElementActivity Fof Library
    fun insertLibraryDataInItem(libraryData:  LibraryData) = viewModelScope.launch {
        useDao.insertLibraryDataInAppInspector(libraryData)
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
