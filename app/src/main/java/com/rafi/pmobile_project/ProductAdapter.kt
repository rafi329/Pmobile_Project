package com.rafi.pmobile_project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(
    private val list: MutableList<Product>,
    private val onAddClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    fun updateData(newList: List<Product>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.imgProduct)
        val nama: TextView = view.findViewById(R.id.txtNama)
        val kategori: TextView = view.findViewById(R.id.txtKategori)
        val harga: TextView = view.findViewById(R.id.txtHarga)
        val stok: TextView = view.findViewById(R.id.txtStok)
        val btnTambah: Button = view.findViewById(R.id.btnTambah)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = list[position]

        holder.img.setImageResource(product.gambar)
        holder.nama.text = product.nama
        holder.kategori.text = product.kategori
        holder.harga.text = "Rp ${product.harga.formatToRupiah()}"
        holder.stok.text = "Stok: ${product.stok}"

        holder.btnTambah.setOnClickListener {
            onAddClick(product)
        }
    }

    override fun getItemCount() = list.size
    private fun Int.formatToRupiah(): String {
        return String.format("%,d", this).replace(',', '.')
    }
}
