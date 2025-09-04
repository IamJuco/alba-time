package com.juco.feature.community

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.juco.designsystem.theme.AlbaTimeTheme
import com.juco.designsystem.topbar.MainTopBar
import com.juco.feature.community.freeboard.FreeBoardTabRoute
import com.juco.feature.community.home.HomeTabRoute
import com.juco.feature.community.qnaboard.QnABoardTabRoute
import kotlinx.coroutines.launch

@Composable
fun CommunityRoute(
    padding: PaddingValues
) {
    CommunityScreen(
        padding = padding
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityScreen(
    padding: PaddingValues
) {
    val tabs = listOf("홈", "자유게시판", "질문게시판")
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        MainTopBar(title = "커뮤니티")

        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.Transparent,
            modifier = Modifier.fillMaxWidth()
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { Text(title) }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { page ->
            when (page) {
                0 -> HomeTabRoute()
                1 -> FreeBoardTabRoute()
                2 -> QnABoardTabRoute()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CommunityScreenPreview() {
    AlbaTimeTheme {
        CommunityScreen(
            padding = PaddingValues()
        )
    }
}