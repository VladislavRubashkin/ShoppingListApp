package com.example.shoppinglistapp.presentation.screens

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.databinding.FragmentShopItemBinding
import com.example.shoppinglistapp.domain.entity.ShopItemEntity
import com.example.shoppinglistapp.presentation.exceptions.BindingException
import com.example.shoppinglistapp.presentation.exceptions.ImplementUseInterfaceException
import com.example.shoppinglistapp.presentation.exceptions.InvalidArgsException
import com.example.shoppinglistapp.presentation.viewmodels.ShopItemViewModel

class ShopItemFragment : Fragment() {

    private var _binding: FragmentShopItemBinding? = null
    private val binding: FragmentShopItemBinding
        get() = _binding ?: throw BindingException("FragmentShopItemBinding == null")

    private val viewModel by lazy {
        ViewModelProvider(this)[ShopItemViewModel::class.java]
    }

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItemEntity.UNDEFINED_ID
    private lateinit var screenCloseListener: ScreenCloseListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ScreenCloseListener) {
            screenCloseListener = context
        } else {
            throw ImplementUseInterfaceException("Activity must implement OnEditingFinishedListener.")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launchRightMode()
        observeViewModel()
        addTextChangesListeners()
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_ADD -> addShopItem()
            MODE_EDIT -> editShopItem()
        }
    }

    private fun addShopItem() {
        binding.saveButton.setOnClickListener {
            viewModel.addShopItem(binding.etName.text.toString(), binding.etCount.text.toString())
        }
    }

    private fun editShopItem() {
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(viewLifecycleOwner) {
            binding.etName.setText(it.name)
            binding.etCount.setText(it.count.toString())
        }
        binding.saveButton.setOnClickListener {
            viewModel.editShopItem(binding.etName.text.toString(), binding.etCount.text.toString())
        }
    }

    private fun observeViewModel() {
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            binding.tilName.error = message
        }
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_count)
            } else {
                null
            }
            binding.tilCount.error = message
        }
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            screenCloseListener.screenClose()
        }
    }

    private fun addTextChangesListeners() {
        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        binding.etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw InvalidArgsException("Param screen mode is absent")
        }
        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw InvalidArgsException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(SHOP_ITEM_ID)) {
                throw InvalidArgsException("Param extra shop item id is absent")
            }
            shopItemId = args.getInt(SHOP_ITEM_ID, ShopItemEntity.UNDEFINED_ID)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface ScreenCloseListener {
        fun screenClose()
    }

    companion object {

        private const val SCREEN_MODE = "extra_mode"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val SHOP_ITEM_ID = "shop_item_id"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(SHOP_ITEM_ID, shopItemId)
                }
            }
        }
    }
}