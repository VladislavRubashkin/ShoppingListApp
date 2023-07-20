package com.example.shoppinglistapp.domain.usecases

import androidx.lifecycle.LiveData
import com.example.shoppinglistapp.domain.entity.ShopItemEntity
import com.example.shoppinglistapp.domain.repository.ShopItemRepository

class GetListShopItemUseCase(
    private val shopItemRepository: ShopItemRepository
) {

    operator fun invoke(): LiveData<List<ShopItemEntity>> {
        return shopItemRepository.getShopItemList()
    }
}