package com.juco.feature.community.model

data class Post(
    val id: String,
    val author: String,
    val title: String,
    val body: String,
    val boardType: String? = "",
    val likeCount: Int = 0,
    val commentCount: Int = 0
)