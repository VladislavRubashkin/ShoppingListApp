package com.example.shoppinglistapp.domain.repository

import androidx.lifecycle.LiveData
import com.example.shoppinglistapp.domain.entity.ShopItemEntity

interface ShopItemRepository {

    fun addShopItem(shopItemEntity: ShopItemEntity)

    fun deleteShopItem(shopItemEntity: ShopItemEntity)

    fun editShopItem(shopItemEntity: ShopItemEntity)

    fun getShopItem(shopItemId: Int): ShopItemEntity

    fun getShopItemList(): LiveData<List<ShopItemEntity>>
}