package com.example.mynotepad.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynotepad.model.database.MainDataBaseInstance
import com.example.mynotepad.activities.ShoppingElementActivity
import com.example.mynotepad.view_model.MainViewModel
import com.example.mynotepad.adapters.ShoppingListAdapter
import com.example.mynotepad.databinding.FragmentShoppingListBinding
import com.example.mynotepad.dialogs.DeleteDialog
import com.example.mynotepad.dialogs.CreateDialog
import com.example.mynotepad.entities.ShoppingListItemData
import com.example.mynotepad.fragments_operation.OptionalFragments
import com.example.mynotepad.utils.TimeManager


class ShoppingListFragment : OptionalFragments(), ShoppingListAdapter.ListenerItem {
   private lateinit var binding: FragmentShoppingListBinding
   private lateinit var intentLauncherInstanceFromShoppingListFragment: ActivityResultLauncher<Intent>
   private lateinit var shoppingListAdapter: ShoppingListAdapter

   private val mainViewModel: MainViewModel by activityViewModels(){
       MainViewModel.MainViewModelFactory((context?.applicationContext as MainDataBaseInstance).mainDataBaseInstance)
   }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentShoppingListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observer()
    }

    override fun onClickAdd() {
        CreateDialog.showDialog(activity as AppCompatActivity, object : CreateDialog.ListenerAction {
            override fun onClickOpenDialog(columnName: String) {
                val shoppingListItemData = ShoppingListItemData(
                    null,
                    columnName,
                    TimeManager.getCurrentTime(),
                    0,
                    0,
                    ""
                )
                mainViewModel.insertShoppingListItemData(shoppingListItemData)
            }

        } ,"")

    }

    private fun initRecyclerView() = with(binding) {
        recyclerViewShoppingList.layoutManager = LinearLayoutManager(activity)
        shoppingListAdapter = ShoppingListAdapter(this@ShoppingListFragment)
        recyclerViewShoppingList.adapter = shoppingListAdapter
    }

    private fun observer() {
        mainViewModel.setAllShoppingLisItemData.observe(viewLifecycleOwner) {
            shoppingListAdapter.submitList(it)
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = ShoppingListFragment()
    }

    override fun deleteShoppingListItemDataAdapterFun(primaryKey: Int) {
        DeleteDialog.showDialog(context as AppCompatActivity, object : DeleteDialog.ListenerAction{
            override fun onClickOpenDialog() {
                mainViewModel.deleteShoppingListItemData(primaryKey, true)
            }
        })
    }

    override fun updateShoppingListItemDataAdapterFun(shoppingListItemData: ShoppingListItemData) {
        CreateDialog.showDialog(activity as AppCompatActivity, object : CreateDialog.ListenerAction {
            override fun onClickOpenDialog(columnName: String) {
                mainViewModel.updateShoppingListItemData(shoppingListItemData.copy(columnName = columnName))
            }
        }, shoppingListItemData.columnName)

    }

    override fun sendShoppingListItemDataForShoppingElementActivityAdapterFun(shoppingListItemData: ShoppingListItemData) {
        val intent = Intent(activity, ShoppingElementActivity::class.java).apply {
            putExtra(ShoppingElementActivity.KEY_SHOPPING_LIST_NAME_DATA_FULL, shoppingListItemData)
        }
       startActivity(intent)

    }
}