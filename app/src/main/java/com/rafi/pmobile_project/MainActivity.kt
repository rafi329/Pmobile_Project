package com.rafi.pmobile_project

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
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
        val editSearch = findViewById<EditText>(R.id.editSearch)

        // ================= DATA PRODUK =================
        val allProducts = listOf(
            Product("Laptop Pro 15\"", "Laptop", 15999000, 15, R.drawable.laptop),
            Product("Wireless Headphone", "Audio", 2499000, 30, R.drawable.wireless_headphone),
            Product("Smartphone X", "Smartphone", 8999000, 20, R.drawable.smartphone),
            Product("Kamera Pro", "Kamera", 12499000, 10, R.drawable.kamera_pro)
        )

        val adapter = ProductAdapter(allProducts.toMutableList()) { product ->
            val existingItem =
                CartActivity.cartItems.find { it.product.nama == product.nama }

            if (existingItem != null) {
                existingItem.quantity++
                Toast.makeText(
                    this,
                    "${product.nama} ditambahkan (Total: ${existingItem.quantity})",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                CartActivity.cartItems.add(CartItem(product))
                Toast.makeText(
                    this,
                    "${product.nama} ditambahkan ke keranjang",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter

        // ================= SEARCH =================
        editSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(
                s: CharSequence?, start: Int, count: Int, after: Int
            ) {}

            override fun onTextChanged(
                s: CharSequence?, start: Int, before: Int, count: Int
            ) {
                val keyword = s.toString().lowercase()
                if (keyword.isEmpty()) {
                    adapter.updateData(allProducts)
                } else {
                    val filtered = allProducts.filter {
                        it.nama.lowercase().contains(keyword)
                    }
                    adapter.updateData(filtered)
                }
            }
        })

        // ================= NAVIGASI =================
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
