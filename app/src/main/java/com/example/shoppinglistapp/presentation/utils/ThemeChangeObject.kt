package com.example.shoppinglistapp.presentation.utils

import android.content.SharedPreferences
import com.example.shoppinglistapp.R

object ThemeChangeObject {

    private const val DEFAULT_THEME = "Light"

    fun getSelectedThem(themPreference: SharedPreferences): Int {
        return if (themPreference.getString("theme_key", "Light") == DEFAULT_THEME) {
            R.style.Theme_ShoppingListLight
        } else {
            R.style.Theme_ShoppingListDark
        }
    }
}