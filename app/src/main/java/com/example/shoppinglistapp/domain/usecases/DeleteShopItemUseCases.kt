package com.example.shoppinglistapp.domain.usecases

import com.example.shoppinglistapp.domain.entity.ShopItemEntity
import com.example.shoppinglistapp.domain.repository.ShopListRepository

class DeleteShopItemUseCases(
    private val shopListRepository: ShopListRepository
) {

    operator fun invoke(shopItemEntity: ShopItemEntity) {
        shopListRepository.deleteShopItem(shopItemEntity)
    }
}