package com.example.shoppinglistapp.presentation.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.databinding.ShopItemDisabledBinding
import com.example.shoppinglistapp.databinding.ShopItemEnabledBinding
import com.example.shoppinglistapp.domain.entity.ShopItemEntity

class ListShopItemAdapter: ListAdapter<ShopItemEntity, ListShopItemViewHolder>(ListShopItemDiffUtilCallback()) {

    var onShopItemClickListener: ((ShopItemEntity) -> Unit)? = null
    var onShopItemLongClickListener: ((ShopItemEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListShopItemViewHolder {
        val layout = when(viewType) {
            VIEW_TYPE_DISABLED -> R.layout.shop_item_disabled
            VIEW_TYPE_ENABLED -> R.layout.shop_item_enabled
            else -> throw RuntimeException("Unknown viewType $viewType")
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        return ListShopItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        val binding = holder.binding
        binding.root.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }
        binding.root.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        when(binding) {
            is ShopItemEnabledBinding -> {
                binding.tvName.text = shopItem.name
                binding.tvCount.text = shopItem.count.toString()
            }
            is ShopItemDisabledBinding -> {
                binding.tvName.text = shopItem.name
                binding.tvCount.text = shopItem.count.toString()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val shopItem = getItem(position)
        return if (shopItem.enabled) {
            VIEW_TYPE_ENABLED
        } else {
            VIEW_TYPE_DISABLED
        }
    }

    companion object {
        private const val VIEW_TYPE_DISABLED = 0
        private const val VIEW_TYPE_ENABLED = 1
        const val RECYCLER_VIEW_POOL = 10
    }
}