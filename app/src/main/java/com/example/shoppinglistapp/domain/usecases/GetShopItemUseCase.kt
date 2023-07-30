package com.example.shoppinglistapp.domain.usecases

import com.example.shoppinglistapp.domain.entity.ShopItemEntity
import com.example.shoppinglistapp.domain.repository.ShopListRepository
import javax.inject.Inject

class GetShopItemUseCase @Inject constructor(
    private val shopListRepository: ShopListRepository
) {

    suspend operator fun invoke(shopItemId: Int): ShopItemEntity {
        return shopListRepository.getShopItem(shopItemId)
    }
}