package com.example.mynotepad.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity (tableName = "table_shopping_list_item_data")
data class ShoppingListItemData(
    @PrimaryKey (autoGenerate = true)
    val columnIdNumberShoppingList: Int?,

    @ColumnInfo (name = "column_1_name")
    val columnName: String,

    @ColumnInfo (name = "column_2_time")
    val columnTime: String,

    @ColumnInfo (name = "column_3_allItemCounter")
    val columnAllItemCounter: Int,

    @ColumnInfo (name = "column_4_checkedItemsCounter")
    val columnCheckedItemCounter :Int,

    @ColumnInfo (name = "column_5_id")
    val columnId: String,

    ) :Serializable
