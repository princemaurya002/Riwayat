package com.princemaurya.rewayat000.ui.grow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.princemaurya.rewayat000.databinding.ItemMaterialBinding

class MaterialAdapter(
    private val items: List<MaterialItem>,
    private val onClick: (MaterialItem) -> Unit
) : RecyclerView.Adapter<MaterialAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemMaterialBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMaterialBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.tvMaterialName.text = item.name
        holder.binding.tvMaterialPrice.text = item.price
        holder.binding.tvMaterialStock.text = item.stock
        holder.binding.tvMaterialSource.text = item.source
        holder.binding.root.setOnClickListener { onClick(item) }
    }

    override fun getItemCount() = items.size
}
