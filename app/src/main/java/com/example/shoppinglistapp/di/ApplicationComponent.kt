package com.example.shoppinglistapp.di

import android.app.Application
import com.example.shoppinglistapp.presentation.screens.ListShopItemActivity
import com.example.shoppinglistapp.presentation.screens.ShopItemFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(activity: ListShopItemActivity)

    fun inject(fragment: ShopItemFragment)

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(
            @BindsInstance application: Application,
        ): ApplicationComponent
    }
}