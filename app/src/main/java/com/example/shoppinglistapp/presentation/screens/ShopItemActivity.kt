package com.example.shoppinglistapp.presentation.screens

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.databinding.ActivityShopItemBinding
import com.example.shoppinglistapp.domain.entity.ShopItemEntity
import com.example.shoppinglistapp.presentation.exceptions.BindingException
import com.example.shoppinglistapp.presentation.exceptions.InvalidParamIntentException

class ShopItemActivity : AppCompatActivity(), ShopItemFragment.ScreenCloseListener {

    private var _binding: ActivityShopItemBinding? = null
    private val binding: ActivityShopItemBinding
        get() = _binding ?: throw BindingException("ActivityShopItemBinding == null")

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItemEntity.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityShopItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        parseIntent()
        if (savedInstanceState == null) {
            launchRightMode()
        }
    }

    private fun launchRightMode() {
        val shopItemFragment = when (screenMode) {
            MODE_ADD -> ShopItemFragment.newInstanceAddItem()
            MODE_EDIT -> ShopItemFragment.newInstanceEditItem(shopItemId)
            else -> throw IllegalArgumentException("Unknown screen mode $screenMode")
        }
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.shop_item_container, shopItemFragment)
            .commit()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw InvalidParamIntentException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw InvalidParamIntentException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw InvalidParamIntentException("Param extra shop item id is absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItemEntity.UNDEFINED_ID)
        }
    }

    override fun screenClose() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        private const val EXTRA_SCREEN_MODE = "extra_screen_mode"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddItem(context: Context): Intent {
            return Intent(context, ShopItemActivity::class.java).apply {
                putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            }
        }

        fun newIntentEditItem(context: Context, shopItemId: Int): Intent {
            return Intent(context, ShopItemActivity::class.java).apply {
                putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
                putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            }
        }
    }
}