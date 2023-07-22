package com.example.shoppinglistapp.domain.usecases

import com.example.shoppinglistapp.domain.entity.ShopItemEntity
import com.example.shoppinglistapp.domain.repository.ShopListRepository

class GetShopItemUseCase(
    private val shopListRepository: ShopListRepository
) {

    suspend operator fun invoke(shopItemId: Int): ShopItemEntity {
        return shopListRepository.getShopItem(shopItemId)
    }
}