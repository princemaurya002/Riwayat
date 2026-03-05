package com.princemaurya.rewayat000.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.princemaurya.rewayat000.R

class HomeCardAdapter(
    private val cards: List<HomeCard>,
    private val onCardClick: (HomeCard) -> Unit
) : RecyclerView.Adapter<HomeCardAdapter.HomeCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home_card, parent, false)
        return HomeCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeCardViewHolder, position: Int) {
        holder.bind(cards[position])
    }

    override fun getItemCount(): Int = cards.size

    inner class HomeCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardView: MaterialCardView = itemView.findViewById(R.id.card_view)
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        private val tvDescription: TextView = itemView.findViewById(R.id.tv_description)
        private val btnAction: MaterialButton = itemView.findViewById(R.id.btn_action)

        fun bind(card: HomeCard) {
            tvTitle.text = card.title
            tvDescription.text = card.description
            btnAction.text = card.actionText

            // Set different colors based on card type
            when (card.type) {
                HomeCardType.ORDERS -> {
                    cardView.setCardBackgroundColor(itemView.context.getColor(R.color.brand_secondary))
                    tvTitle.setTextColor(itemView.context.getColor(R.color.white))
                    tvDescription.setTextColor(itemView.context.getColor(R.color.white))
                    btnAction.setBackgroundColor(itemView.context.getColor(R.color.white))
                    btnAction.setTextColor(itemView.context.getColor(R.color.brand_primary))
                }
                HomeCardType.SCHEME -> {
                    cardView.setCardBackgroundColor(itemView.context.getColor(R.color.brand_primary))
                    tvTitle.setTextColor(itemView.context.getColor(R.color.white))
                    tvDescription.setTextColor(itemView.context.getColor(R.color.white))
                    btnAction.setBackgroundColor(itemView.context.getColor(R.color.white))
                    btnAction.setTextColor(itemView.context.getColor(R.color.brand_primary))
                }
                HomeCardType.FAIR -> {
                    cardView.setCardBackgroundColor(itemView.context.getColor(R.color.brand_primary_dark))
                    tvTitle.setTextColor(itemView.context.getColor(R.color.white))
                    tvDescription.setTextColor(itemView.context.getColor(R.color.white))
                    btnAction.setBackgroundColor(itemView.context.getColor(R.color.white))
                    btnAction.setTextColor(itemView.context.getColor(R.color.brand_primary))
                }
                HomeCardType.TIP -> {
                    cardView.setCardBackgroundColor(itemView.context.getColor(R.color.background))
                    tvTitle.setTextColor(itemView.context.getColor(R.color.brand_primary))
                    tvDescription.setTextColor(itemView.context.getColor(R.color.brand_primary))
                    btnAction.setBackgroundColor(itemView.context.getColor(R.color.brand_primary))
                    btnAction.setTextColor(itemView.context.getColor(R.color.white))
                }
                HomeCardType.ANALYTICS -> {
                    cardView.setCardBackgroundColor(itemView.context.getColor(R.color.brand_secondary))
                    tvTitle.setTextColor(itemView.context.getColor(R.color.white))
                    tvDescription.setTextColor(itemView.context.getColor(R.color.white))
                    btnAction.setBackgroundColor(itemView.context.getColor(R.color.white))
                    btnAction.setTextColor(itemView.context.getColor(R.color.brand_primary))
                }
            }

            btnAction.setOnClickListener {
                onCardClick(card)
            }

            cardView.setOnClickListener {
                onCardClick(card)
            }
        }
    }
}
