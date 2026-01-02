package com.rafi.pmobile_project

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TransactionHistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_history)

        val rv = findViewById<RecyclerView>(R.id.rvTransaction)
        val tvEmpty = findViewById<TextView>(R.id.tvEmptyTransaction)
        val btnHome = findViewById<Button>(R.id.btnBackHome)
        val list = CheckoutActivity.receiptList

        btnHome.setOnClickListener {
            finish() // kembali ke beranda
        }

        if (list.isEmpty()) {
            tvEmpty.visibility = View.VISIBLE
            rv.visibility = View.GONE
            return
        }

        tvEmpty.visibility = View.GONE
        rv.visibility = View.VISIBLE

        val adapter = TransactionAdapter(list) { receipt ->
            val i = Intent(this, ReceiptActivity::class.java)
            i.putExtra("RECEIPT_ID", receipt.id)
            startActivity(i)
        }

        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
    }



}

