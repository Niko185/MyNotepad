package com.example.mynotepad.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity (tableName = "table_note_item_data")
data class NoteItemData(

    @PrimaryKey(autoGenerate = true)
    val primaryKey: Int?,

    @ColumnInfo (name = "column_name")
    val columnName: String,

    @ColumnInfo (name = "column_description")
    val columnDescription: String,

    @ColumnInfo (name = "column_time")
    val columnTime: String,

    @ColumnInfo(name = "column_category")
     val columnCategory: String

    ): Serializable
