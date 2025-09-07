package com.juco.feature.community.model

data class Post(
    val id: String,
    val title: String,
    val author: String = "",
    val likeCount: Int = 0,
    val commentCount: Int = 0
)