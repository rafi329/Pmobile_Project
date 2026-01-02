package com.rafi.pmobile_project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TransactionAdapter(
    private val list: MutableList<TransactionReceipt>,
    private val onClick: (TransactionReceipt) -> Unit
) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvId: TextView = v.findViewById(R.id.tvTransId)
        val tvTotal: TextView = v.findViewById(R.id.tvTransTotal)
        val card: LinearLayout = v.findViewById(R.id.cardTransaction)
        val btnDelete: TextView = v.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(p: ViewGroup, t: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(p.context)
                .inflate(R.layout.item_transaction, p, false)
        )

    override fun onBindViewHolder(h: ViewHolder, i: Int) {
        val r = list[i]
        h.tvId.text = "Transaksi #${r.id}"
        h.tvTotal.text = "Rp ${r.totalPrice.formatToRupiah()}"

        h.card.setOnClickListener { onClick(r) }

        h.btnDelete.setOnClickListener {
            list.removeAt(h.adapterPosition)
            notifyItemRemoved(h.adapterPosition)
            notifyItemRangeChanged(h.adapterPosition, list.size)
        }
    }

    override fun getItemCount() = list.size

    fun Int.formatToRupiah(): String =
        String.format("%,d", this).replace(',', '.')
}
