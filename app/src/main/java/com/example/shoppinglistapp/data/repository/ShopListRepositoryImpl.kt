package com.example.shoppinglistapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.shoppinglistapp.data.database.ShopListDao
import com.example.shoppinglistapp.data.mapper.ShopListMapper
import com.example.shoppinglistapp.domain.entity.ShopItemEntity
import com.example.shoppinglistapp.domain.repository.ShopListRepository
import javax.inject.Inject

class ShopListRepositoryImpl @Inject constructor(
    private val shopListDao: ShopListDao,
    private val mapper: ShopListMapper
) : ShopListRepository {


    override suspend fun addShopItem(shopItemEntity: ShopItemEntity) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItemEntity))
    }

    override suspend fun deleteShopItem(shopItemEntity: ShopItemEntity) {
        shopListDao.deleteShopItem(shopItemEntity.id)
    }

    override suspend fun editShopItem(shopItemEntity: ShopItemEntity) {
        shopListDao.editShopItem(mapper.mapEntityToDbModel(shopItemEntity))
    }

    override suspend fun getShopItem(shopItemId: Int): ShopItemEntity {
        val shopItemDbModel = shopListDao.getShopItem(shopItemId)
        return mapper.mapDbModelToEntity(shopItemDbModel)
    }

    override fun getShopItemList(): LiveData<List<ShopItemEntity>> =
        MediatorLiveData<List<ShopItemEntity>>().apply {
            addSource(shopListDao.getListShopItem()) {
                value = mapper.mapListDbModelToListEntity(it)
            }
        }
}