package com.princemaurya.rewayat000.data.model

import kotlinx.serialization.Serializable

@Serializable
data class MarketingContent(
    val title: String,
    val description: String,
    val socialMediaCaption: String,
    val hashtags: List<String>,
    val regionalTranslation: String? = null,
    val adCopy: String? = null,
    val searchKeywords: List<String>
)
