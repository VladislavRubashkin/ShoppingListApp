package com.example.shoppinglistapp.domain.usecases

import androidx.lifecycle.LiveData
import com.example.shoppinglistapp.domain.entity.ShopItemEntity
import com.example.shoppinglistapp.domain.repository.ShopListRepository
import javax.inject.Inject

class GetListShopItemUseCase @Inject constructor(
    private val shopListRepository: ShopListRepository
) {

    operator fun invoke(): LiveData<List<ShopItemEntity>> {
        return shopListRepository.getShopItemList()
    }
}