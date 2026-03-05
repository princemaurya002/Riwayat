package com.princemaurya.rewayat000.ui.shop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.princemaurya.rewayat000.R

class OrderAdapter(
    private val orders: List<Order>,
    private val onOrderClick: (Order) -> Unit
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orders[position])
    }

    override fun getItemCount(): Int = orders.size

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardView: MaterialCardView = itemView.findViewById(R.id.card_view)
        private val tvOrderId: TextView = itemView.findViewById(R.id.tv_order_id)
        private val tvProductName: TextView = itemView.findViewById(R.id.tv_product_name)
        private val tvAmount: TextView = itemView.findViewById(R.id.tv_amount)
        private val tvStatus: TextView = itemView.findViewById(R.id.tv_status)
        private val tvDate: TextView = itemView.findViewById(R.id.tv_date)
        private val btnAction: MaterialButton = itemView.findViewById(R.id.btn_action)

        fun bind(order: Order) {
            tvOrderId.text = "Order #${order.orderId}"
            tvProductName.text = order.productName
            tvAmount.text = order.amount
            tvStatus.text = order.status
            tvDate.text = order.date

            // Set button text and color based on status
            when (order.status) {
                "New" -> {
                    btnAction.text = "Accept Order"
                    btnAction.setBackgroundColor(itemView.context.getColor(R.color.brand_primary))
                    btnAction.setTextColor(itemView.context.getColor(R.color.white))
                }
                "Processing" -> {
                    btnAction.text = "Track Shipment"
                    btnAction.setBackgroundColor(itemView.context.getColor(R.color.brand_secondary))
                    btnAction.setTextColor(itemView.context.getColor(R.color.white))
                }
                "Shipped" -> {
                    btnAction.text = "Track Package"
                    btnAction.setBackgroundColor(itemView.context.getColor(R.color.brand_secondary))
                    btnAction.setTextColor(itemView.context.getColor(R.color.white))
                }
                "Delivered" -> {
                    btnAction.text = "View Details"
                    btnAction.setBackgroundColor(itemView.context.getColor(R.color.brand_primary_dark))
                    btnAction.setTextColor(itemView.context.getColor(R.color.white))
                }
            }

            btnAction.setOnClickListener {
                onOrderClick(order)
            }

            cardView.setOnClickListener {
                onOrderClick(order)
            }
        }
    }
}
