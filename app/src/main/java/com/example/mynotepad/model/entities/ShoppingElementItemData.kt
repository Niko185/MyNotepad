package com.example.mynotepad.entities

import androidx.room.*
import java.io.Serializable

@Entity (tableName = "table_shopping_element_item_data")
data class ShoppingElementItemData(

    @PrimaryKey(autoGenerate = true)
    val columnIdNumberShoppingElement: Int?,

    @ColumnInfo (name = "column_1_name")
    val columnName: String,

    @ColumnInfo (name = "column_2_content")
    val columnContent: String?,

    @ColumnInfo (name = "column_3_checked")
    val columnChecked: Boolean = false,

    @ColumnInfo (name = "column_4_id")
    val columnId: Int,

    @ColumnInfo (name = "column_5_type")
    val columnType: Int = 0

    ) :Serializable
