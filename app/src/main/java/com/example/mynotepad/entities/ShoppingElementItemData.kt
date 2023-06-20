package com.example.mynotepad.entities

import androidx.room.*
import java.io.Serializable

@Entity (tableName = "table_shopping_element_item_data")
data class ShoppingElementItemData(

    @PrimaryKey(autoGenerate = true)
    val primaryKey: Int?,

    @ColumnInfo (name = "column_name")
    val columnName: String,

    @ColumnInfo (name = "column_content")
    val columnContent: String?,

    @ColumnInfo (name = "column_checked")
    val columnChecked: Boolean = false,

    @ColumnInfo (name = "column_id")
    val columnId: Int,

    @ColumnInfo (name = "column_type")
    val columnType: Int = 0

    ) :Serializable
