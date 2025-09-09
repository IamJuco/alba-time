package com.juco.feature.community.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juco.designsystem.textfield.CategoryText
import com.juco.designsystem.theme.AlbaTimeTheme
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.remember
import androidx.compose.ui.text.style.TextOverflow
import com.juco.designsystem.theme.Blue
import com.juco.designsystem.theme.White
import com.juco.feature.community.model.BoardCategoryEnum
import com.juco.feature.community.model.Post

@Composable
fun HomeTabRoute(
    onNavigateToBoard: (BoardCategoryEnum) -> Unit = {},
    onOpenPost: (String) -> Unit = {}
) {
    // TODO: ViewModel state로 교체 예정
    val popular = remember { samplePosts("인기", 5) }
    val free = remember { samplePosts("자유", 5) }
    val qna = remember { samplePosts("질문", 5) }

    HomeTabScreen(
        popular = popular,
        free = free,
        qna = qna,
        onMoreClick = onNavigateToBoard,
        onPostClick = { onOpenPost(it.id) }
    )
}

@Composable
fun HomeTabScreen(
    popular: List<Post>,
    free: List<Post>,
    qna: List<Post>,
    onMoreClick: (BoardCategoryEnum) -> Unit = {},
    onPostClick: (Post) -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            CategorySection(
                title = "인기글",
                posts = popular.take(3),
                onMoreClick = { onMoreClick(BoardCategoryEnum.POPULAR) },
                onPostClick = onPostClick
            )
        }
        item {
            CategorySection(
                title = "자유게시판",
                posts = free.take(3),
                onMoreClick = { onMoreClick(BoardCategoryEnum.FREE) },
                onPostClick = onPostClick
            )
        }
        item {
            CategorySection(
                title = "질문게시판",
                posts = qna.take(3),
                onMoreClick = { onMoreClick(BoardCategoryEnum.QNA) },
                onPostClick = onPostClick
            )
        }
    }
}

@Composable
private fun CategorySection(
    title: String,
    posts: List<Post>,
    onMoreClick: () -> Unit,
    onPostClick: (Post) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CategoryText(text = title)
        }

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            if (posts.isEmpty()) {
                Text(
                    text = "표시할 게시글이 없어요.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            } else {
                posts.forEach { post ->
                    DefaultPostRow(post = post, onClick = { onPostClick(post) })
                }
            }
        }

        OutlinedButton(
            onClick = onMoreClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            contentPadding = PaddingValues(horizontal = 12.dp)
        ) {
            Text("더보기")
            Spacer(Modifier.width(6.dp))
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
private fun DefaultPostRow(
    post: Post,
    onClick: () -> Unit
) {
    Surface(
        tonalElevation = 10.dp,
        border = BorderStroke(width = 1.dp, color = Blue),
        shape = MaterialTheme.shapes.large,
        color = White,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)

    ) {
        Column(Modifier.padding(12.dp)) {
            Text(
                text = post.title,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.height(6.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (post.author.isNotBlank()) {
                    Text(
                        text = post.author,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = "❤ ${post.likeCount} · 💬 ${post.commentCount}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

private fun samplePosts(prefix: String, count: Int) = List(count) { i ->
    Post(
        id = "$prefix-$i",
        title = "$prefix 게시글 제목 $i",
        author = "익명$i",
        likeCount = (i + 1) * 3,
        commentCount = i,
        body = "",
        boardType = "",
    )
}

@Preview(showBackground = true)
@Composable
private fun HomeTabScreenPreview() {
    AlbaTimeTheme {
        HomeTabRoute()
    }
}