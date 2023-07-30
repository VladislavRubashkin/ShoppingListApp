package com.example.shoppinglistapp.di

import android.app.Application
import com.example.shoppinglistapp.data.database.AppDatabase
import com.example.shoppinglistapp.data.database.ShopListDao
import com.example.shoppinglistapp.data.repository.ShopListRepositoryImpl
import com.example.shoppinglistapp.domain.repository.ShopListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindShopListRepository(impl: ShopListRepositoryImpl): ShopListRepository

    companion object {

        @ApplicationScope
        @Provides
        fun provideShopListDao(
            application: Application
        ): ShopListDao {
            return AppDatabase.getInstance(application).shopListDao()
        }
    }
}