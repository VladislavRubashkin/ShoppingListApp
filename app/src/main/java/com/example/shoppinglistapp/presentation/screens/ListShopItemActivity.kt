package com.example.shoppinglistapp.presentation.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.databinding.ActivityMainBinding
import com.example.shoppinglistapp.presentation.adapters.ShopItemAdapter
import com.example.shoppinglistapp.presentation.exeptions.BindingException
import com.example.shoppinglistapp.presentation.viewmodels.ListShopItemViewModel

class ListShopItemActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding ?: throw BindingException("ActivityMainBinding == null")

    private val viewModel: ListShopItemViewModel by lazy {
        ViewModelProvider(this)[ListShopItemViewModel::class.java]
    }

    private lateinit var shopItemAdapter: ShopItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getShopItemList()
        addShopItem()
        initRecyclerView()
    }

    private fun getShopItemList() {
        viewModel.listShopItem.observe(this) {
            shopItemAdapter.submitList(it)
        }
    }

    private fun addShopItem() {
        binding.fabAdd.setOnClickListener {
            val intent = ShopItemActivity.newIntentAddItem(this)
            startActivity(intent)
        }
    }

    private fun initRecyclerView() {
        shopItemAdapter = ShopItemAdapter()
        with(binding) {
            rcViewShopItem.adapter = shopItemAdapter
            rcViewShopItem.recycledViewPool.setMaxRecycledViews(
                R.layout.shop_item_enabled, ShopItemAdapter.RECYCLER_VIEW_POOL
            )
            rcViewShopItem.recycledViewPool.setMaxRecycledViews(
                R.layout.shop_item_disabled, ShopItemAdapter.RECYCLER_VIEW_POOL
            )
        }
        setupClickListener()
        setupLongClickListener()
        setupSwipeListener(binding.rcViewShopItem)
    }

    private fun setupClickListener() {
        shopItemAdapter.onShopItemClickListener = {
            val intent = ShopItemActivity.newIntentEditItem(this, it.id)
            startActivity(intent)
        }
    }

    private fun setupLongClickListener() {
        shopItemAdapter.onShopItemLongClickListener = {
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
                val shopItem = shopItemAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(shopItem)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rcView)
    }

    //    override fun screenClose() {
//        supportFragmentManager.popBackStack()
//    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}