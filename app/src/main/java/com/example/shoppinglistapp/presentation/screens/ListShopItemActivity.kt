package com.example.shoppinglistapp.presentation.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.databinding.ActivityListShopItemBinding
import com.example.shoppinglistapp.presentation.adapters.ListShopItemAdapter
import com.example.shoppinglistapp.presentation.exceptions.BindingException
import com.example.shoppinglistapp.presentation.viewmodels.ListShopItemViewModel

class ListShopItemActivity : AppCompatActivity() {

    private var _binding: ActivityListShopItemBinding? = null
    private val binding: ActivityListShopItemBinding
        get() = _binding ?: throw BindingException("ActivityNameShopListBinding == null")

    private val viewModel: ListShopItemViewModel by lazy {
        ViewModelProvider(this)[ListShopItemViewModel::class.java]
    }

    private lateinit var listShopItemAdapter: ListShopItemAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityListShopItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getShopItemList()
        addShopItem()
        initRecyclerView()
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

    private fun initRecyclerView() {
        listShopItemAdapter = ListShopItemAdapter()
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