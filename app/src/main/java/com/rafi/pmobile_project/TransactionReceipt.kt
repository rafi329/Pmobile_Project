package com.rafi.pmobile_project

data class TransactionReceipt(
    val id: Int,
    val name: String,
    val address: String,
    val phone: String,
    val paymentMethod: String,
    val totalItems: Int,
    val totalPrice: Int,
    val items: List<CartItem>
)

