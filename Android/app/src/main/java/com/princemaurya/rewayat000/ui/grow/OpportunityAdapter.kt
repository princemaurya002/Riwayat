package com.princemaurya.rewayat000.ui.grow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.princemaurya.rewayat000.R

class OpportunityAdapter(
    private val opportunities: List<Opportunity>,
    private val onOpportunityClick: (Opportunity) -> Unit
) : RecyclerView.Adapter<OpportunityAdapter.OpportunityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OpportunityViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_opportunity, parent, false)
        return OpportunityViewHolder(view)
    }

    override fun onBindViewHolder(holder: OpportunityViewHolder, position: Int) {
        holder.bind(opportunities[position])
    }

    override fun getItemCount(): Int = opportunities.size

    inner class OpportunityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardView: MaterialCardView = itemView.findViewById(R.id.card_view)
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        private val tvDeadline: TextView = itemView.findViewById(R.id.tv_deadline)
        private val tvDescription: TextView = itemView.findViewById(R.id.tv_description)
        private val btnAction: MaterialButton = itemView.findViewById(R.id.btn_action)

        fun bind(opportunity: Opportunity) {
            tvTitle.text = opportunity.title
            tvDeadline.text = opportunity.deadline
            tvDescription.text = opportunity.description
            btnAction.text = opportunity.actionText

            // Set different colors based on opportunity type
            when (opportunity.type) {
                OpportunityType.FAIR -> {
                    cardView.setCardBackgroundColor(itemView.context.getColor(R.color.brand_primary))
                    tvTitle.setTextColor(itemView.context.getColor(R.color.white))
                    tvDeadline.setTextColor(itemView.context.getColor(R.color.white))
                    tvDescription.setTextColor(itemView.context.getColor(R.color.white))
                    btnAction.setBackgroundColor(itemView.context.getColor(R.color.white))
                    btnAction.setTextColor(itemView.context.getColor(R.color.brand_primary))
                }
                OpportunityType.CORPORATE -> {
                    cardView.setCardBackgroundColor(itemView.context.getColor(R.color.brand_secondary))
                    tvTitle.setTextColor(itemView.context.getColor(R.color.white))
                    tvDeadline.setTextColor(itemView.context.getColor(R.color.white))
                    tvDescription.setTextColor(itemView.context.getColor(R.color.white))
                    btnAction.setBackgroundColor(itemView.context.getColor(R.color.white))
                    btnAction.setTextColor(itemView.context.getColor(R.color.brand_primary))
                }
                OpportunityType.WHOLESALER -> {
                    cardView.setCardBackgroundColor(itemView.context.getColor(R.color.brand_primary_dark))
                    tvTitle.setTextColor(itemView.context.getColor(R.color.white))
                    tvDeadline.setTextColor(itemView.context.getColor(R.color.white))
                    tvDescription.setTextColor(itemView.context.getColor(R.color.white))
                    btnAction.setBackgroundColor(itemView.context.getColor(R.color.white))
                    btnAction.setTextColor(itemView.context.getColor(R.color.brand_primary))
                }
                OpportunityType.EXPORT -> {
                    cardView.setCardBackgroundColor(itemView.context.getColor(R.color.background))
                    tvTitle.setTextColor(itemView.context.getColor(R.color.brand_primary))
                    tvDeadline.setTextColor(itemView.context.getColor(R.color.brand_primary))
                    tvDescription.setTextColor(itemView.context.getColor(R.color.brand_primary))
                    btnAction.setBackgroundColor(itemView.context.getColor(R.color.brand_primary))
                    btnAction.setTextColor(itemView.context.getColor(R.color.white))
                }
            }

            btnAction.setOnClickListener {
                onOpportunityClick(opportunity)
            }

            cardView.setOnClickListener {
                onOpportunityClick(opportunity)
            }
        }
    }
}
