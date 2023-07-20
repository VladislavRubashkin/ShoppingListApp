package com.example.shoppinglistapp.domain.usecases

import androidx.lifecycle.LiveData
import com.example.shoppinglistapp.domain.entity.ShopItemEntity
import com.example.shoppinglistapp.domain.repository.ShopListRepository

class GetListShopItemUseCase(
    private val shopListRepository: ShopListRepository
) {

    operator fun invoke(): LiveData<List<ShopItemEntity>> {
        return shopListRepository.getShopItemList()
    }
}