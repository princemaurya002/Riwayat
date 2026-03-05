package com.princemaurya.rewayat000.ui.operations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.princemaurya.rewayat000.R

class OperationToolAdapter(
    private val tools: List<OperationTool>,
    private val onToolClick: (OperationTool) -> Unit
) : RecyclerView.Adapter<OperationToolAdapter.OperationToolViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OperationToolViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_operation_tool, parent, false)
        return OperationToolViewHolder(view)
    }

    override fun onBindViewHolder(holder: OperationToolViewHolder, position: Int) {
        holder.bind(tools[position])
    }

    override fun getItemCount(): Int = tools.size

    inner class OperationToolViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardView: MaterialCardView = itemView.findViewById(R.id.card_view)
        private val ivIcon: ImageView = itemView.findViewById(R.id.iv_icon)
        private val tvName: TextView = itemView.findViewById(R.id.tv_name)
        private val tvDescription: TextView = itemView.findViewById(R.id.tv_description)

        fun bind(tool: OperationTool) {
            ivIcon.setImageResource(tool.iconRes)
            tvName.text = tool.name
            tvDescription.text = tool.description

            cardView.setOnClickListener {
                onToolClick(tool)
            }
        }
    }
}
