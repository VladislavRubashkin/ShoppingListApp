package com.example.shoppinglistapp.presentation.screens

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.databinding.ActivityListShopItemBinding
import com.example.shoppinglistapp.presentation.ShoppingListApplication
import com.example.shoppinglistapp.presentation.adapters.ListShopItemAdapter
import com.example.shoppinglistapp.presentation.exceptions.BindingException
import com.example.shoppinglistapp.presentation.viewmodels.ListShopItemViewModel
import com.example.shoppinglistapp.presentation.viewmodels.ViewModelFactory
import javax.inject.Inject

class ListShopItemActivity : AppCompatActivity() {

    private var _binding: ActivityListShopItemBinding? = null
    private val binding: ActivityListShopItemBinding
        get() = _binding ?: throw BindingException("ActivityNameShopListBinding == null")

    private lateinit var listShopItemAdapter: ListShopItemAdapter

    private val textSizePreference by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: ListShopItemViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ListShopItemViewModel::class.java]
    }

    private val component by lazy {
        (application as ShoppingListApplication).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)

        super.onCreate(savedInstanceState)
        _binding = ActivityListShopItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getShopItemList()
        addShopItem()
        setupSettings()
        initRecyclerView()
    }

    override fun onRestart() {
        super.onRestart()
        recreate()
    }

    private fun getShopItemList() {
        viewModel.listShopItem.observe(this) {
            listShopItemAdapter.submitList(it)
        }
    }

    private fun addShopItem() {
        binding.fabAdd.setOnClickListener {
            val intent = ShopItemActivity.newIntentAddItem(this)
            startActivity(intent)
        }
    }

    private fun setupSettings() {
        binding.fabSettings.setOnClickListener {
            Intent(this, SettingsActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun initRecyclerView() {
        listShopItemAdapter = ListShopItemAdapter(textSizePreference)
        with(binding) {
            rcViewShopItem.adapter = listShopItemAdapter
            rcViewShopItem.recycledViewPool.setMaxRecycledViews(
                R.layout.shop_item_enabled, ListShopItemAdapter.RECYCLER_VIEW_POOL
            )
            rcViewShopItem.recycledViewPool.setMaxRecycledViews(
                R.layout.shop_item_disabled, ListShopItemAdapter.RECYCLER_VIEW_POOL
            )
        }
        setupClickListener()
        setupLongClickListener()
        setupSwipeListener(binding.rcViewShopItem)
    }

    private fun setupClickListener() {
        listShopItemAdapter.onShopItemClickListener = {
            val intent = ShopItemActivity.newIntentEditItem(this, it.id)
            startActivity(intent)
        }
    }

    private fun setupLongClickListener() {
        listShopItemAdapter.onShopItemLongClickListener = {
            viewModel.editShopItem(it)
        }
    }

    private fun setupSwipeListener(rcView: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val shopItem = listShopItemAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(shopItem)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rcView)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}