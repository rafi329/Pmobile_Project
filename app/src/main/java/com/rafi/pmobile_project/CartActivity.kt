package com.rafi.pmobile_project

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartActivity : AppCompatActivity() {

    private lateinit var cartAdapter: CartAdapter
    private lateinit var tvTotalPrice: TextView
    private lateinit var tvEmptyCart: TextView
    private lateinit var recyclerView: RecyclerView

    companion object {
        var cartItems = mutableListOf<CartItem>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val btnBack: ImageView = findViewById(R.id.btnBack)
        tvTotalPrice = findViewById(R.id.tvTotalPrice)
        tvEmptyCart = findViewById(R.id.tvEmptyCart)
        val btnCheckout: Button = findViewById(R.id.btnCheckout)
        recyclerView = findViewById(R.id.rvCartItems)

        cartAdapter = CartAdapter(cartItems) {
            updateTotalPrice()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = cartAdapter

        btnBack.setOnClickListener { finish() }

        btnCheckout.setOnClickListener {
            if (cartItems.isEmpty()) {
                Toast.makeText(this, "Keranjang kosong!", Toast.LENGTH_SHORT).show()
            } else {
                val selectedItems = cartItems.filter { it.isSelected }
                if (selectedItems.isEmpty()) {
                    Toast.makeText(this, "Pilih minimal 1 item!", Toast.LENGTH_SHORT).show()
                } else {
                    startActivity(Intent(this, CheckoutActivity::class.java))
                }
            }
        }

        updateUI()
    }

    private fun updateUI() {
        if (cartItems.isEmpty()) {
            tvEmptyCart.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
            tvTotalPrice.text = "Total: Rp 0"
        } else {
            tvEmptyCart.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            updateTotalPrice()
        }
    }

    private fun updateTotalPrice() {
        val total = cartItems.filter { it.isSelected }.sumOf { it.totalPrice }
        tvTotalPrice.text = "Total: Rp ${total.formatToRupiah()}"
    }

    override fun onResume() {
        super.onResume()
        cartAdapter.notifyDataSetChanged()
        updateUI()
    }

    fun Int.formatToRupiah(): String =
        String.format("%,d", this).replace(',', '.')
}
