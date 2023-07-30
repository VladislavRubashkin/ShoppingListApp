package com.example.shoppinglistapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglistapp.domain.entity.ShopItemEntity
import com.example.shoppinglistapp.domain.usecases.DeleteShopItemUseCase
import com.example.shoppinglistapp.domain.usecases.EditShopItemUseCase
import com.example.shoppinglistapp.domain.usecases.GetListShopItemUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListShopItemViewModel @Inject constructor(
    private val getListShopItemUseCase: GetListShopItemUseCase,
    private val deleteShopItemUseCase: DeleteShopItemUseCase,
    private val editShopItemUseCase: EditShopItemUseCase
) : ViewModel() {

    val listShopItem = getListShopItemUseCase()

    fun deleteShopItem(shopItemEntity: ShopItemEntity) {
        viewModelScope.launch {
            deleteShopItemUseCase(shopItemEntity)
        }
    }

    fun editShopItem(shopItemEntity: ShopItemEntity) {
        viewModelScope.launch {
            val newShopItemEntity = shopItemEntity.copy(enabled = !shopItemEntity.enabled)
            editShopItemUseCase(newShopItemEntity)
        }
    }
}