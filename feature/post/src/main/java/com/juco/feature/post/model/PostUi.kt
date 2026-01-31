package com.juco.feature.post.model

data class PostUi(
    val title: String,
    val content: String,
    val author: String,
    val createdAt: String,
    val likeCount: Int,
    val isLiked: Boolean,
    val comments: List<CommentUi>,
)