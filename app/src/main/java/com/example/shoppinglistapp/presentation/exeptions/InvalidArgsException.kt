package com.example.shoppinglistapp.presentation.exeptions

class InvalidArgsException(override val message: String) : RuntimeException(message) {
}