package com.example.mynotepad.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity (tableName = "table_shopping_list_item_data")
data class ShoppingListItemData(
    @PrimaryKey (autoGenerate = true)
    val primaryKey: Int?,

    @ColumnInfo (name = "column_name")
    val columnName: String,

    @ColumnInfo (name = "column_time")
    val columnTime: String,

    @ColumnInfo (name = "column_elementInList")
    val columnElementInList: Int,

    @ColumnInfo (name = "column_checkedItemsCounter")
    val columnCheckedItemCounter :Int,

    @ColumnInfo (name = "column_id")
    val columnId: String,

    ) :Serializable
