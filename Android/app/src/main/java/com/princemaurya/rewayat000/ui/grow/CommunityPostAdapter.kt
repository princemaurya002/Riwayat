package com.princemaurya.rewayat000.ui.grow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.princemaurya.rewayat000.R

class CommunityPostAdapter(
    private val posts: List<CommunityPost>,
    private val onPostClick: (CommunityPost) -> Unit
) : RecyclerView.Adapter<CommunityPostAdapter.CommunityPostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityPostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_community_post, parent, false)
        return CommunityPostViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommunityPostViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount(): Int = posts.size

    inner class CommunityPostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardView: MaterialCardView = itemView.findViewById(R.id.card_view)
        private val ivAuthor: ImageView = itemView.findViewById(R.id.iv_author)
        private val tvAuthorName: TextView = itemView.findViewById(R.id.tv_author_name)
        private val tvAuthorRole: TextView = itemView.findViewById(R.id.tv_author_role)
        private val tvTimeAgo: TextView = itemView.findViewById(R.id.tv_time_ago)
        private val tvContent: TextView = itemView.findViewById(R.id.tv_content)
        private val ivPostImage: ImageView = itemView.findViewById(R.id.iv_post_image)
        private val tvLikes: TextView = itemView.findViewById(R.id.tv_likes)
        private val tvComments: TextView = itemView.findViewById(R.id.tv_comments)

        fun bind(post: CommunityPost) {
            ivAuthor.setImageResource(post.imageRes)
            tvAuthorName.text = post.authorName
            tvAuthorRole.text = post.authorRole
            tvTimeAgo.text = post.timeAgo
            tvContent.text = post.content
            ivPostImage.setImageResource(post.imageRes)
            tvLikes.text = "${post.likes} likes"
            tvComments.text = "${post.comments} comments"

            cardView.setOnClickListener {
                onPostClick(post)
            }
        }
    }
}
