package com.example.shoppinglistapp.presentation

import android.app.Application
import android.content.SharedPreferences
import com.example.shoppinglistapp.di.DaggerApplicationComponent

class ShoppingListApplication : Application() {

    val component by lazy {
        DaggerApplicationComponent
            .factory()
            .create(this)
    }
}