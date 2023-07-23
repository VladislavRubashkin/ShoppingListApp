package com.example.shoppinglistapp.presentation.utils

import android.content.SharedPreferences
import android.widget.TextView

object ChangeTextSize {

    private const val DEFAULT_TEXT_SIZE = "16"

    fun setTextSize(tvName: TextView, tvCount: TextView, preference: SharedPreferences) {
        tvName.convertInputFormatTextSize(preference.getString("text_size_key", DEFAULT_TEXT_SIZE))
        tvCount.convertInputFormatTextSize(preference.getString("text_size_key", DEFAULT_TEXT_SIZE))
    }

    private fun TextView.convertInputFormatTextSize(size: String?) {
        if (size != null) {
            this.textSize = size.toFloat()
        }
    }
}