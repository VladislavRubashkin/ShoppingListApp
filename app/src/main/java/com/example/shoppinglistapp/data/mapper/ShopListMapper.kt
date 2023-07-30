package com.example.shoppinglistapp.data.mapper

import com.example.shoppinglistapp.data.database.ShopItemDbModel
import com.example.shoppinglistapp.domain.entity.ShopItemEntity
import javax.inject.Inject

class ShopListMapper @Inject constructor() {

    fun mapEntityToDbModel(shopItemEntity: ShopItemEntity): ShopItemDbModel {
        return ShopItemDbModel(
            id = shopItemEntity.id,
            name = shopItemEntity.name,
            count = shopItemEntity.count,
            enabled = shopItemEntity.enabled
        )
    }

    fun mapDbModelToEntity(shopItemDbModel: ShopItemDbModel): ShopItemEntity {
        return ShopItemEntity(
            id = shopItemDbModel.id,
            name = shopItemDbModel.name,
            count = shopItemDbModel.count,
            enabled = shopItemDbModel.enabled
        )
    }

    fun mapListDbModelToListEntity(shopItemList: List<ShopItemDbModel>) = shopItemList.map {
        mapDbModelToEntity(it)
    }
}