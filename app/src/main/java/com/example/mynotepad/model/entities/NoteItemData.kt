package com.example.mynotepad.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity (tableName = "table_note_item_data")
data class NoteItemData(


    @PrimaryKey(autoGenerate = true)
    val columnIdNumberNote: Int?,

    @ColumnInfo (name = "column_1_name")
    val columnName: String,

    @ColumnInfo (name = "column_2_description")
    val columnDescription: String,

    @ColumnInfo (name = "column_3_time")
    val columnTime: String,

    @ColumnInfo(name = "column_4_category")
     val columnCategory: String

    ): Serializable
