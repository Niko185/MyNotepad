package com.example.mynotepad.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity (tableName = "table_library_item_data")
data class LibraryItemData(
    @PrimaryKey(autoGenerate = true)
    val columnIdNumberLibrary: Int?,

    @ColumnInfo (name = "column_1_name")
    val columnName: String

    ): Serializable


