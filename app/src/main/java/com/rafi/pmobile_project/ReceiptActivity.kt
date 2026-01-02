package com.rafi.pmobile_project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReceiptActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipt)

        val tvReceipt = findViewById<TextView>(R.id.tvSummary)
        val btnNext = findViewById<Button>(R.id.btnNext)

        val receiptId = intent.getIntExtra("RECEIPT_ID", -1)
        val receipt = CheckoutActivity.receiptList.find { it.id == receiptId }

        if (receipt != null) {
            val header = """
                ID Transaksi : ${receipt.id}
                Nama         : ${receipt.name}
                Alamat       : ${receipt.address}
                No. Telepon  : ${receipt.phone}
                Pembayaran   : ${receipt.paymentMethod}
                Total Item   : ${receipt.totalItems}
                Total Bayar  : Rp ${receipt.totalPrice.formatToRupiah()}

                Daftar Barang:
            """.trimIndent()

            val body = receipt.items.joinToString("\n") {
                "- ${it.product.nama} x${it.quantity} → Rp ${it.totalPrice.formatToRupiah()}"
            }

            tvReceipt.text = header + "\n\n" + body
        } else {
            tvReceipt.text = "Struk tidak ditemukan!"
        }

        btnNext.setOnClickListener {
            startActivity(Intent(this, TransactionHistoryActivity::class.java))
            finish()
        }
    }

    fun Int.formatToRupiah(): String =
        String.format("%,d", this).replace(',', '.')
}
