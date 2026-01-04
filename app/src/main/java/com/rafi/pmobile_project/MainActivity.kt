package com.rafi.pmobile_project

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.beranda)

        val recyclerView = findViewById<RecyclerView>(R.id.rvProducts)
        val btnCart = findViewById<ImageView>(R.id.btnCart)
        val btnReceipt = findViewById<ImageView>(R.id.btnReceiptList)
        val btnProfile = findViewById<ImageView>(R.id.btnProfile)

        val produkList = listOf(
            Product("Laptop Pro 15\"", "Laptop", 15999000, 15, R.drawable.laptop),
            Product("Wireless Headphone", "Audio", 2499000, 30, R.drawable.wireless_headphone),
            Product("Smartphone X", "Smartphone", 8999000, 20, R.drawable.smartphone),
            Product("Kamera Pro", "Kamera", 12499000, 10, R.drawable.kamera_pro)
        )

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = ProductAdapter(produkList) { product ->
            val existingItem = CartActivity.cartItems.find { it.product.nama == product.nama }

            if (existingItem != null) {
                existingItem.quantity++
                Toast.makeText(
                    this,
                    "${product.nama} ditambahkan ke keranjang (Total: ${existingItem.quantity})",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                CartActivity.cartItems.add(CartItem(product))
                Toast.makeText(this, "${product.nama} ditambahkan ke keranjang", Toast.LENGTH_SHORT).show()
            }
        }

        btnCart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        btnReceipt.setOnClickListener {
            startActivity(Intent(this, TransactionHistoryActivity::class.java))
        }

        btnProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}
