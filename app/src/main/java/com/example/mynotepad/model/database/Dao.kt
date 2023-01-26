package com.example.mynotepad.model.database

import androidx.room.*
import androidx.room.Dao
import com.example.mynotepad.entities.LibraryData
import com.example.mynotepad.entities.NoteItemData
import com.example.mynotepad.entities.ShoppingElementItemData
import com.example.mynotepad.entities.ShoppingListItemData
import kotlinx.coroutines.flow.Flow

@Dao

interface Dao {

        // NoteFragment Function Database
    @Query ("SELECT * FROM table_note_item_data")
    fun getAllNoteItemData() : Flow<List<NoteItemData>>

    @Insert
    suspend fun insertNoteItemData(noteItemData: NoteItemData)

    @Update
    suspend fun updateNoteItemData(noteItemData: NoteItemData)

    @Query ("DELETE FROM table_note_item_data WHERE columnIdNumberNote IS :columnIdNumberNote")
    suspend fun deleteNoteItemData(columnIdNumberNote: Int)


        // ShoppingListFragment Functions Database
    @Query ("SELECT * FROM table_shopping_list_item_data")
    fun getAllShoppingListItemDataDAOFUN(): Flow<List<ShoppingListItemData>>

    @Insert
    suspend fun insertShoppingListItemDataDAOFUN(shoppingListItemData: ShoppingListItemData)

    @Update
    suspend fun updateShoppingListItemDataDAOFUN(shoppingListItemData: ShoppingListItemData)

    @Query("DELETE FROM table_shopping_list_item_data WHERE columnIdNumberShoppingList IS :columnIdNumberShoppingList")
    suspend fun deleteShoppingListItemDataDAOFUN(columnIdNumberShoppingList: Int)


        //ShoppingElementActivity Functions Database
    @Query ("SELECT * FROM table_shopping_element_item_data WHERE column_4_id LIKE :columnId")
    fun getAllShoppingElementItemData(columnId: Int): Flow<List<ShoppingElementItemData>>

    @Insert
    suspend fun insertShoppingElementItemData(shoppingElementItemData: ShoppingElementItemData)

    @Update
    suspend fun updateShoppingElementItemData(shoppingElementItemData: ShoppingElementItemData)

    @Query ("DELETE FROM table_shopping_element_item_data WHERE column_4_id LIKE :columnId")
    suspend fun deleteShoppingElementItemData(columnId: Int)

        // LibraryData Functions DataBase
    @Query ("SELECT * FROM table_library_item_data WHERE column_name LIKE :columnName")
    suspend fun getAllLibraryItemData(columnName: String) : List<LibraryData>

    @Insert
    suspend fun insertLibraryDataInAppInspector(libraryData: LibraryData) {
    }

}