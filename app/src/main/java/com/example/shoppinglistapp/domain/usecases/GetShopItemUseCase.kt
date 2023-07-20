package com.example.shoppinglistapp.domain.usecases

import com.example.shoppinglistapp.domain.entity.ShopItemEntity
import com.example.shoppinglistapp.domain.repository.ShopItemRepository

class GetShopItemUseCase(
    private val shopItemRepository: ShopItemRepository
) {

    operator fun invoke(shopItemId: Int): ShopItemEntity {
        return shopItemRepository.getShopItem(shopItemId)
    }
}