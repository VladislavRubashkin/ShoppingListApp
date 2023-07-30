package com.example.shoppinglistapp.di

import androidx.lifecycle.ViewModel
import com.example.shoppinglistapp.presentation.viewmodels.ListShopItemViewModel
import com.example.shoppinglistapp.presentation.viewmodels.ShopItemViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ListShopItemViewModel::class)
    fun bindListShopItemViewModel(viewModel: ListShopItemViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShopItemViewModel::class)
    fun bindShopItemViewModel(viewModel: ShopItemViewModel): ViewModel
}