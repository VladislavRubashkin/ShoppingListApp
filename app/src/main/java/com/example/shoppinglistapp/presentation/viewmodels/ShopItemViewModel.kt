package com.example.shoppinglistapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglistapp.domain.entity.ShopItemEntity
import com.example.shoppinglistapp.domain.usecases.AddShopItemUseCase
import com.example.shoppinglistapp.domain.usecases.EditShopItemUseCase
import com.example.shoppinglistapp.domain.usecases.GetShopItemUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject


class ShopItemViewModel @Inject constructor(
    private val getShopItemUseCase: GetShopItemUseCase,
    private val addShopItemUseCase: AddShopItemUseCase,
    private val editShopItemUseCase: EditShopItemUseCase
) : ViewModel() {


    private val _shopItem = MutableLiveData<ShopItemEntity>()
    val shopItem: LiveData<ShopItemEntity>
        get() = _shopItem

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen


    fun getShopItem(shopItemId: Int) {
        viewModelScope.launch {
            val shopItem = getShopItemUseCase(shopItemId)
            _shopItem.value = shopItem
        }
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val parseName = parseInputName(inputName)
        val parseCount = parseInputCount(inputCount)
        if (validateInputFields(parseName, parseCount)) {
            viewModelScope.launch {
                val shopItem = ShopItemEntity(name = parseName, count = parseCount, enabled = true)
                addShopItemUseCase(shopItem)
                finish()
            }
        }
    }

    fun editShopItem(inputNewName: String?, inputNewCount: String?) {
        val parseNewName = parseInputName(inputNewName)
        val parseNewCount = parseInputCount(inputNewCount)
        if (validateInputFields(parseNewName, parseNewCount)) {
            _shopItem.value.let {
                viewModelScope.launch {
                    val editedShopItem = it?.copy(name = parseNewName, count = parseNewCount)!!
                    editShopItemUseCase(editedShopItem)
                    finish()
                }
            }
        }
    }

    private fun parseInputName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseInputCount(inputCount: String?): Double {
        return try {
            inputCount?.trim()?.toDouble() ?: 0.0
        } catch (e: NumberFormatException) {
            0.0
        }
    }

    private fun validateInputFields(inputName: String, inputCount: Double): Boolean {
        var result = true
        if (inputName.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (inputCount <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun finish() {
        _shouldCloseScreen.value = Unit
    }
}