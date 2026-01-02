package com.rafi.pmobile_project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CheckoutActivity : AppCompatActivity() {

    companion object {
        var receiptList = mutableListOf<TransactionReceipt>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val btnBack: ImageView = findViewById(R.id.btnBack)
        val tvItems: TextView = findViewById(R.id.tvItems)
        val tvTotalPrice: TextView = findViewById(R.id.tvTotalPrice)
        val editName: EditText = findViewById(R.id.editName)
        val editAddress: EditText = findViewById(R.id.editAddress)
        val editPhone: EditText = findViewById(R.id.editPhone)
        val radioCash: RadioButton = findViewById(R.id.radioCash)
        val radioTransfer: RadioButton = findViewById(R.id.radioTransfer)
        val btnConfirm: Button = findViewById(R.id.btnConfirm)

        btnBack.setOnClickListener { finish() }

        val selectedItems = CartActivity.cartItems.filter { it.isSelected }
        val totalPrice = selectedItems.sumOf { it.totalPrice }
        val totalItems = selectedItems.sumOf { it.quantity }

        tvItems.text = "$totalItems item"
        tvTotalPrice.text = "Rp ${totalPrice.formatToRupiah()}"

        btnConfirm.setOnClickListener {
            val name = editName.text.toString().trim()
            val address = editAddress.text.toString().trim()
            val phone = editPhone.text.toString().trim()
            val paymentMethod = if (radioCash.isChecked) "Cash on Delivery (COD)" else "Transfer Bank"

            if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Harap lengkapi semua data!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newReceipt = TransactionReceipt(
                id = receiptList.size + 1,
                name = name,
                address = address,
                phone = phone,
                paymentMethod = paymentMethod,
                totalItems = totalItems,
                totalPrice = totalPrice,
                items = selectedItems
            )

            receiptList.add(newReceipt)
            CartActivity.cartItems.removeAll { it.isSelected }

            val intent = Intent(this, TransactionHistoryActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun Int.formatToRupiah(): String =
        String.format("%,d", this).replace(',', '.')
}
