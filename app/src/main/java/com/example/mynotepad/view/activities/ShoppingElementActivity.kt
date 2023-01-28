package com.example.mynotepad.activities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynotepad.R
import com.example.mynotepad.model.database.MainDataBaseInstance
import com.example.mynotepad.view_model.MainViewModel
import com.example.mynotepad.databinding.ActivityShoppingElementBinding
import com.example.mynotepad.entities.LibraryItemData
import com.example.mynotepad.entities.ShoppingElementItemData
import com.example.mynotepad.entities.ShoppingListItemData
import com.example.mynotepad.utils.share.ShareHelper
import com.example.mynotepad.view.dialogs.DialogEditElement
import com.example.mynotepad.view_model.adapters.ShoppingElementAdapter

class ShoppingElementActivity : AppCompatActivity(), ShoppingElementAdapter.ListenerItem {
    private lateinit var binding: ActivityShoppingElementBinding
    private lateinit var saveIconMenuItem: MenuItem
    private lateinit var newIconMenuItem: MenuItem
    private lateinit var editTextLibraryItem: EditText
    private lateinit var shoppingListItemData: ShoppingListItemData
    private  lateinit var shoppingElementItemData: ShoppingElementItemData
    private lateinit var  shoppingElementAdapter: ShoppingElementAdapter
    private lateinit var textWatcher: TextWatcher
    private lateinit var libraryItemData: LibraryItemData


    private val mainViewModel: MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory((applicationContext as MainDataBaseInstance).mainDataBaseInstance)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingElementBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getAllShoppingListNameData()
        initRcViewElementActivity()
        observer()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_for_shopping_element_activity, menu)
        saveIconMenuItem = menu?.findItem(R.id.saveIcon)!!
        newIconMenuItem = menu.findItem(R.id.newIcon)!!
        editTextLibraryItem = newIconMenuItem.actionView.findViewById(R.id.edNewItem) as EditText
        saveIconMenuItem.isVisible = false
        newIconMenuItem.setOnActionExpandListener(expandActionView())
        textWatcher = textWatcher()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.saveIcon -> {
                createElementItem()
                createLibraryData()
            }
            R.id.deleteShoppingList -> {
                mainViewModel.deleteShoppingListItemData(shoppingListItemData.primaryKey!!, true)
                finish()
            }
            R.id.clearShoppingList -> {
                mainViewModel.deleteShoppingListItemData(shoppingListItemData.primaryKey!!, false)
            }

            R.id.shareShoppingList -> {
                startActivity(Intent.createChooser(
                    ShareHelper.shareShoppingList(shoppingElementAdapter.currentList, shoppingListItemData.columnName),
                    "Share by"
                ))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createElementItem() {
        if(editTextLibraryItem.text.toString().isEmpty()) return
        val shoppingElementItemData = ShoppingElementItemData(
            null,
            editTextLibraryItem.text.toString(),
            null,
            false,
            shoppingListItemData.primaryKey!!,
            0
        )
        editTextLibraryItem.setText("")
        mainViewModel.insertShoppingElementItemData(shoppingElementItemData)
    }

    private fun createLibraryData() {
        if(editTextLibraryItem.text.toString().isEmpty()) return
        libraryItemData = LibraryItemData(
            null,
            editTextLibraryItem.text.toString()
        )
        mainViewModel.insertLibraryData(libraryItemData)
    }

    private fun expandActionView(): MenuItem.OnActionExpandListener {
        return object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                saveIconMenuItem.isVisible = true
                editTextLibraryItem.addTextChangedListener(textWatcher)
                return true
            }
            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                saveIconMenuItem.isVisible = false
                editTextLibraryItem.removeTextChangedListener(textWatcher)
                invalidateOptionsMenu()
                return true
            }
        }
    }

    private fun initRcViewElementActivity() = with(binding) {
          rcViewElementActivity.layoutManager = LinearLayoutManager(this@ShoppingElementActivity)
          shoppingElementAdapter = ShoppingElementAdapter(this@ShoppingElementActivity)
          rcViewElementActivity.adapter = shoppingElementAdapter
    }

    private fun observer() {
        mainViewModel.setAllShoppingElementItemData(shoppingListItemData.primaryKey!!).observe(this) {
            shoppingElementAdapter.submitList(it)
            binding.tvEmpty.visibility = if(it.isEmpty()) {
                View.VISIBLE
            } else View.GONE
        }
    }

    private fun getAllShoppingListNameData() {
        shoppingListItemData = intent.getSerializableExtra(KEY_SHOPPING_LIST_NAME_DATA_FULL) as ShoppingListItemData
       // binding.tvTestText.text = shoppingListItemData.columnName
    }
    companion object {
        const val KEY_SHOPPING_LIST_NAME_DATA_FULL = "key_shopping_list_name_data_full"
    }




    private fun textWatcher():  TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Not Use
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("myLog", "Text chair: $p0")
            }

            override fun afterTextChanged(p0: Editable?) {
                // Not Use
            }

        }
    }


    override fun onClickAllItemAdapterFun(shoppingElementItemData: ShoppingElementItemData, stateConstant: Int) {
        when(stateConstant){
            ShoppingElementAdapter.CHEK_BOX_ICON_PRESSED-> mainViewModel.updateShoppingElementItemData(shoppingElementItemData)
            ShoppingElementAdapter.EDIT_ICON_PRESSED -> openEditDialog(shoppingElementItemData)
        }

    }

    override fun deleteShoppingElementItemDataAdapterFun(primaryKey: Int) {

    }

    private fun openEditDialog(shoppingElementItemData: ShoppingElementItemData) = with(binding){
        DialogEditElement.showDialog(this@ShoppingElementActivity, shoppingElementItemData, object: DialogEditElement.ListenerAction {
            override fun onClickOpenDialog(item: ShoppingElementItemData) {
                mainViewModel.updateShoppingElementItemData(item)
            }

        })
    }
}