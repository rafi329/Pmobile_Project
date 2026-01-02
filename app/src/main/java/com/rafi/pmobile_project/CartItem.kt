package com.rafi.pmobile_project

import kotlin.times

data class CartItem(
    val product: Product,
    var quantity: Int = 1,
    var isSelected: Boolean = true
) {
    val totalPrice: Int
        get() = product.harga * quantity
}
