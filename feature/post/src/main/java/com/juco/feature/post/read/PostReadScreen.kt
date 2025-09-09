package com.juco.feature.post.read

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.juco.designsystem.topbar.PreviousTopBar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.juco.designsystem.theme.AlbaTimeTheme
import com.juco.designsystem.theme.Blue
import com.juco.feature.post.component.Separator
import com.juco.feature.post.model.CommentUi
import com.juco.feature.post.model.PostUi

@Composable
fun PostReadRoute(
    padding: PaddingValues,
    popBackStack: () -> Unit,
    onToggleLike: () -> Unit = {},
    onMoreClick: () -> Unit = {}
) {
    val dummyState = remember {
        PostUi(
            title = "테스트 제목",
            content = "테스트 본문 내용입니다.",
            author = "주코",
            createdAt = "2025.09.07 23:01",
            likeCount = 10,
            isLiked = true,
            comments = emptyList(),
        )
    }

    PostReadScreen(
        padding = padding,
        popBackStack = popBackStack,
        uiState = dummyState,
        onToggleLike = onToggleLike,
        onMoreClick = onMoreClick
    )
}

@Composable
fun PostReadScreen(
    padding: PaddingValues,
    popBackStack: () -> Unit,
    uiState: PostUi,
    onToggleLike: () -> Unit = {},
    onMoreClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(MaterialTheme.colorScheme.background)
    ) {
        PreviousTopBar(
            title = "",
            onPopBackStack = popBackStack,
            centerEndAction = {
                IconButton(onClick = onMoreClick) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "게시판 메뉴보기",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )

        HeaderSection(
            title = uiState.title,
            author = uiState.author,
            dateText = uiState.createdAt,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        )

        Separator(thickness = 1.dp)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 24.dp, top = 12.dp)
        ) {
            item {
                SelectionContainer {
                    Text(
                        text = uiState.content,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary,
                        lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
                    )
                }
            }

            item {
                ActionRow(
                    likeCount = uiState.likeCount,
                    isLiked = uiState.isLiked,
                    onToggleLike = onToggleLike
                )
            }

            item { Separator(thickness = 8.dp) }

            item {
                Text(
                    text = "댓글 ${uiState.comments.size}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            items(uiState.comments, key = { it.id }) { comment ->
                CommentItem(comment = comment)
                Separator(thickness = 1.dp)
            }
        }
    }
}

@Composable
private fun HeaderSection(
    title: String,
    author: String,
    dateText: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(6.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = author,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = " • $dateText",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun ActionRow(
    likeCount: Int,
    isLiked: Boolean,
    onToggleLike: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilledTonalButton(
            onClick = onToggleLike,
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
            colors = ButtonDefaults.filledTonalButtonColors(
                containerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
            ),
            border = BorderStroke(width = 1.dp, color = Blue)
        ) {
            Icon(
                imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = if (isLiked) "좋아요 취소" else "좋아요",
            )
            Spacer(Modifier.width(8.dp))
            Text("$likeCount")
        }

        Spacer(Modifier.weight(1f))

    }
}

@Composable
private fun CommentItem(comment: CommentUi) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = comment.author,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = " • ${comment.createdAt}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 6.dp)
            )
        }
        Spacer(Modifier.height(6.dp))
        SelectionContainer {
            Text(
                text = comment.content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PostReadScreenPreview() {
    val previewState = PostUi(
        title = "제목123123123123",
        content = "안녕하세요 감사해요 잘있어요 다시만나요".trimIndent(),
        author = "주코",
        createdAt = "2025.09.07 23:01",
        likeCount = 15,
        isLiked = false,
        comments = listOf(
            CommentUi(
                id = "1",
                author = "익명1",
                content = "댓글1234",
                createdAt = "2025.09.07 23:01",
            ),
            CommentUi(
                id = "2",
                author = "익명123",
                content = "댓글12",
                createdAt = "2025.09.07 23:01",
            )
        )
    )
    AlbaTimeTheme {
        PostReadScreen(
            padding = PaddingValues(),
            popBackStack = {},
            uiState = previewState
        )
    }
}