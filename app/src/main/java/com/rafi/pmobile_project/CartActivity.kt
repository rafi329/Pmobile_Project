package com.rafi.pmobile_project



import android.content.Intent
import android.os.Bundle
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

        // Inisialisasi views
        val btnBack: ImageView = findViewById(R.id.btnBack)
        tvTotalPrice = findViewById(R.id.tvTotalPrice)
        tvEmptyCart = findViewById(R.id.tvEmptyCart)
        val btnCheckout: Button = findViewById(R.id.btnCheckout)
        recyclerView = findViewById(R.id.rvCartItems)

        // Setup RecyclerView
        cartAdapter = CartAdapter(cartItems) { updatedItems ->
            updateTotalPrice()
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = cartAdapter

        // Back button
        btnBack.setOnClickListener {
            finish()
        }

        // Checkout button
        btnCheckout.setOnClickListener {
            if (cartItems.isEmpty()) {
                Toast.makeText(this, "Keranjang kosong!", Toast.LENGTH_SHORT).show()
            } else {
                val selectedItems = cartItems.filter { it.isSelected }
                if (selectedItems.isEmpty()) {
                    Toast.makeText(this, "Pilih minimal 1 item!", Toast.LENGTH_SHORT).show()
                } else {
                    val intent = Intent(this, CheckoutActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        updateUI()
    }

    private fun updateUI() {
        if (cartItems.isEmpty()) {
            tvEmptyCart.visibility = TextView.VISIBLE
            recyclerView.visibility = RecyclerView.GONE
        } else {
            tvEmptyCart.visibility = TextView.GONE
            recyclerView.visibility = RecyclerView.VISIBLE
            updateTotalPrice()
        }
    }

    private fun updateTotalPrice() {
        val total = cartItems
            .filter { it.isSelected }
            .sumOf { it.totalPrice }
        tvTotalPrice.text = "Total: Rp ${total.formatToRupiah()}"
    }

    override fun onResume() {
        super.onResume()
        cartAdapter.notifyDataSetChanged()
        updateUI()
    }

    fun Int.formatToRupiah(): String {
        return String.format("%,d", this).replace(',', '.')
    }
}