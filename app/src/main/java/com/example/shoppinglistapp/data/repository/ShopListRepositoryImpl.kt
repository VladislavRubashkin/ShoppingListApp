package com.example.shoppinglistapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.shoppinglistapp.data.database.AppDatabase
import com.example.shoppinglistapp.data.database.ShopListDao
import com.example.shoppinglistapp.data.mapper.ShopListMapper
import com.example.shoppinglistapp.domain.entity.ShopItemEntity
import com.example.shoppinglistapp.domain.repository.ShopListRepository

class ShopListRepositoryImpl(
    application: Application
) : ShopListRepository {

    private val shopListDao: ShopListDao = AppDatabase.getInstance(application).shopListDao()
    private val mapper = ShopListMapper()

    override fun addShopItem(shopItemEntity: ShopItemEntity) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItemEntity))
    }

    override fun deleteShopItem(shopItemEntity: ShopItemEntity) {
        shopListDao.deleteShopItem(shopItemEntity.id)
    }

    override fun editShopItem(shopItemEntity: ShopItemEntity) {
        shopListDao.editShopItem(mapper.mapEntityToDbModel(shopItemEntity))
    }

    override fun getShopItem(shopItemId: Int): ShopItemEntity {
        val shopItemDbModel = shopListDao.getShopItem(shopItemId)
        return mapper.mapDbModelToEntity(shopItemDbModel)
    }

    override fun getShopItemList(): LiveData<List<ShopItemEntity>> = MediatorLiveData<List<ShopItemEntity>>().apply {
        addSource(shopListDao.getListShopItem()) {
            value = mapper.mapListDbModelToListEntity(it)
        }
    }
}