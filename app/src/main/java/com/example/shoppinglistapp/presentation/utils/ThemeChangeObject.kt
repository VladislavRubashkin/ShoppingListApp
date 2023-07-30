package com.example.shoppinglistapp.presentation.utils

import android.content.SharedPreferences
import com.example.shoppinglistapp.R

object ThemeChangeObject {

    private const val LIGHT_THEME = "Light"

    fun getSelectedThem(themePreference: SharedPreferences): Int {
        return if (themePreference.getString("theme_key", "Light") == LIGHT_THEME) {
            R.style.Theme_ShoppingListLight
        } else {
            R.style.Theme_ShoppingListDark
        }
    }
}