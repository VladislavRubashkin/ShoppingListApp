package com.example.shoppinglistapp.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.shoppinglistapp.domain.entity.ShopItemEntity


class ListShopItemDiffUtilCallback: DiffUtil.ItemCallback<ShopItemEntity>() {
    override fun areItemsTheSame(oldItem: ShopItemEntity, newItem: ShopItemEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShopItemEntity, newItem: ShopItemEntity): Boolean {
        return oldItem == newItem
    }

}