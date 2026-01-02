package com.rafi.pmobile_project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import kotlin.compareTo
import kotlin.dec
import kotlin.inc
import kotlin.toString

class CartAdapter(
    private val cartItems: MutableList<CartItem>,
    private val onCartUpdated: (MutableList<CartItem>) -> Unit
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkBox: CheckBox = view.findViewById(R.id.checkBox)
        val imgProduct: ImageView = view.findViewById(R.id.imgProduct)
        val txtName: TextView = view.findViewById(R.id.txtName)
        val txtPrice: TextView = view.findViewById(R.id.txtPrice)
        val txtQuantity: TextView = view.findViewById(R.id.txtQuantity)
        val btnMinus: ImageButton = view.findViewById(R.id.btnMinus)
        val btnPlus: ImageButton = view.findViewById(R.id.btnPlus)
        val btnDelete: ImageButton = view.findViewById(R.id.btnDelete)
        val txtTotal: TextView = view.findViewById(R.id.txtTotal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartItem = cartItems[position]
        val product = cartItem.product

        holder.imgProduct.setImageResource(product.gambar)
        holder.txtName.text = product.nama
        holder.txtPrice.text = "Rp ${product.harga.formatToRupiah()}"
        holder.txtQuantity.text = cartItem.quantity.toString()
        holder.txtTotal.text = "Rp ${cartItem.totalPrice.formatToRupiah()}"
        holder.checkBox.isChecked = cartItem.isSelected

        // CheckBox listener
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            cartItem.isSelected = isChecked
            onCartUpdated(cartItems)
        }

        // Plus button
        holder.btnPlus.setOnClickListener {
            cartItem.quantity++
            holder.txtQuantity.text = cartItem.quantity.toString()
            holder.txtTotal.text = "Rp ${cartItem.totalPrice.formatToRupiah()}"
            onCartUpdated(cartItems)
        }

        // Minus button
        holder.btnMinus.setOnClickListener {
            if (cartItem.quantity > 1) {
                cartItem.quantity--
                holder.txtQuantity.text = cartItem.quantity.toString()
                holder.txtTotal.text = "Rp ${cartItem.totalPrice.formatToRupiah()}"
                onCartUpdated(cartItems)
            }
        }

        // Delete button
        holder.btnDelete.setOnClickListener {
            cartItems.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, cartItems.size)
            onCartUpdated(cartItems)
        }
    }

    override fun getItemCount() = cartItems.size

    private fun Int.formatToRupiah(): String {
        return String.format("%,d", this).replace(',', '.')
    }
}