package com.example.shoppinglistapp.domain.usecases

import com.example.shoppinglistapp.domain.entity.ShopItemEntity
import com.example.shoppinglistapp.domain.repository.ShopItemRepository

class EditShopItemUseCase(
    private val shopItemRepository: ShopItemRepository
) {

    operator fun invoke(shopItemEntity: ShopItemEntity) {
        shopItemRepository.editShopItem(shopItemEntity)
    }
}