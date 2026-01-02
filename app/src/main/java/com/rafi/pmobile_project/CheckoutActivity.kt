package com.rafi.pmobile_project

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class CheckoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        // Inisialisasi views
        val btnBack: ImageView = findViewById(R.id.btnBack)
        val tvItems: TextView = findViewById(R.id.tvItems)
        val tvTotalPrice: TextView = findViewById(R.id.tvTotalPrice)
        val editName: EditText = findViewById(R.id.editName)
        val editAddress: EditText = findViewById(R.id.editAddress)
        val editPhone: EditText = findViewById(R.id.editPhone)
        val radioCash: RadioButton = findViewById(R.id.radioCash)
        val radioTransfer: RadioButton = findViewById(R.id.radioTransfer)
        val btnConfirm: Button = findViewById(R.id.btnConfirm)

        // Hitung total dan item
        val selectedItems = CartActivity.cartItems.filter { it.isSelected }
        val totalPrice = selectedItems.sumOf { it.totalPrice }
        val totalItems = selectedItems.sumOf { it.quantity }

        tvItems.text = "$totalItems item"
        tvTotalPrice.text = "Rp ${totalPrice.formatToRupiah()}"

        // Back button
        btnBack.setOnClickListener {
            finish()
        }

        // Confirm button
        btnConfirm.setOnClickListener {
            val name = editName.text.toString().trim()
            val address = editAddress.text.toString().trim()
            val phone = editPhone.text.toString().trim()
            val paymentMethod = if (radioCash.isChecked) "Cash" else "Transfer Bank"

            if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Harap lengkapi semua data!", Toast.LENGTH_SHORT).show()
            } else {
                // Proses checkout
                CartActivity.cartItems.removeAll { it.isSelected }

                Toast.makeText(
                    this,
                    "Pesanan berhasil! Total: Rp ${totalPrice.formatToRupiah()}",
                    Toast.LENGTH_LONG
                ).show()

                finish()
            }
        }
    }

    private fun Int.formatToRupiah(): String {
        return String.format("%,d", this).replace(',', '.')
    }
}