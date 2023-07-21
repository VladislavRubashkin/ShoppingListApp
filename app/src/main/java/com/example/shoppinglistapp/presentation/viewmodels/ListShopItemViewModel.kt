package com.example.shoppinglistapp.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglistapp.data.repository.ShopListRepositoryImpl
import com.example.shoppinglistapp.domain.entity.ShopItemEntity
import com.example.shoppinglistapp.domain.usecases.DeleteShopItemUseCase
import com.example.shoppinglistapp.domain.usecases.EditShopItemUseCase
import com.example.shoppinglistapp.domain.usecases.GetListShopItemUseCase
import kotlinx.coroutines.launch

class ListShopItemViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repo = ShopListRepositoryImpl(application)
    private val getListShopItemUseCase = GetListShopItemUseCase(repo)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repo)
    private val editShopItemUseCase = EditShopItemUseCase(repo)


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