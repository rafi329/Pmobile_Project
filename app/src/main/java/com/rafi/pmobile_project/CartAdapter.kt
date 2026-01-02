package com.rafi.pmobile_project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(
    private val cartItems: MutableList<CartItem>,
    private val onCartUpdated: () -> Unit
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_cart, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartItem = cartItems[position]
        val product = cartItem.product

        holder.checkBox.setOnCheckedChangeListener(null)

        holder.imgProduct.setImageResource(product.gambar)
        holder.txtName.text = product.nama
        holder.txtPrice.text = "Rp ${product.harga.formatToRupiah()}"
        holder.txtQuantity.text = cartItem.quantity.toString()
        holder.txtTotal.text = "Rp ${cartItem.totalPrice.formatToRupiah()}"
        holder.checkBox.isChecked = cartItem.isSelected

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            cartItem.isSelected = isChecked
            onCartUpdated()
        }

        holder.btnPlus.setOnClickListener {
            cartItem.quantity++
            notifyItemChanged(position)
            onCartUpdated()
        }

        holder.btnMinus.setOnClickListener {
            if (cartItem.quantity > 1) {
                cartItem.quantity--
                notifyItemChanged(position)
                onCartUpdated()
            }
        }

        holder.btnDelete.setOnClickListener {
            cartItems.removeAt(position)
            notifyItemRemoved(position)
            onCartUpdated()
        }
    }

    override fun getItemCount() = cartItems.size

    private fun Int.formatToRupiah(): String =
        String.format("%,d", this).replace(',', '.')
}
