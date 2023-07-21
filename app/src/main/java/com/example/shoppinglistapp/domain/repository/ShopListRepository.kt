package com.example.shoppinglistapp.domain.repository

import androidx.lifecycle.LiveData
import com.example.shoppinglistapp.domain.entity.ShopItemEntity

interface ShopListRepository {

    suspend fun addShopItem(shopItemEntity: ShopItemEntity)

    suspend fun deleteShopItem(shopItemEntity: ShopItemEntity)

    suspend fun editShopItem(shopItemEntity: ShopItemEntity)

    suspend fun getShopItem(shopItemId: Int): ShopItemEntity

    fun getShopItemList(): LiveData<List<ShopItemEntity>>
}