package com.example.mynotepad.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mynotepad.entities.LibraryItemData
import com.example.mynotepad.entities.NoteItemData
import com.example.mynotepad.entities.ShoppingElementItemData
import com.example.mynotepad.entities.ShoppingListItemData

@Database(entities = [LibraryItemData::class, NoteItemData::class, ShoppingElementItemData::class, ShoppingListItemData::class], version = 1)
abstract class MainDataBase : RoomDatabase() {

    abstract fun getUseDao() : Dao

    companion object {
        @Volatile
        private var INSTANCE_MAIN_DATA_BASE: MainDataBase? = null
        fun getMainDataBase(context: Context): MainDataBase {
            return INSTANCE_MAIN_DATA_BASE ?:

                synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, MainDataBase::class.java, "my_notepad.db").build()
                instance
            }
        }
    }
}