package com.juco.feature.post.create

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juco.designsystem.theme.AlbaTimeTheme
import com.juco.designsystem.topbar.PreviousTopBar
import com.juco.feature.post.component.Separator
import com.juco.feature.post.model.BoardCategoryEnum

@Composable
fun PostCreateRoute(
    padding: PaddingValues,
    popBackStack: () -> Unit
) {
    PostCreateScreen(
        padding = padding,
        popBackStack = popBackStack
    )
}

@Composable
fun PostCreateScreen(
    padding: PaddingValues,
    popBackStack: () -> Unit,
    isSubmitting: Boolean = false,
    onSubmit: (category: BoardCategoryEnum, title: String, content: String) -> Unit = { _, _, _ -> }
) {
    val focusManager = LocalFocusManager.current

    val writableBoards = remember { listOf(BoardCategoryEnum.FREE, BoardCategoryEnum.QNA) }

    var selectedName by remember { mutableStateOf(BoardCategoryEnum.FREE.name) }
    val selected = remember(selectedName) { BoardCategoryEnum.valueOf(selectedName) }

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    val titleMax = 50
    val contentMax = 2_000

    val titleError = title.isBlank()
    val contentError = content.isBlank()
    val isValid = !titleError && !contentError && (selected in writableBoards) && !isSubmitting

    var categoryMenuExpanded by remember { mutableStateOf(false) }
    var categoryFieldWidthPx by remember { mutableIntStateOf(0) }
    val density = LocalDensity.current
    val interaction = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(MaterialTheme.colorScheme.background)
    ) {
        PreviousTopBar(
            title = "게시글 작성",
            onPopBackStack = popBackStack
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 24.dp, top = 12.dp)
        ) {
            item {
                Text(
                    text = "새 글 작성",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            item { Separator(thickness = 1.dp) }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coords ->
                            categoryFieldWidthPx = coords.size.width
                        }
                ) {
                    OutlinedTextField(
                        value = displayNameOf(selected),
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("카테고리") },
                        trailingIcon = {
                            Icon(
                                imageVector = if (categoryMenuExpanded) Icons.Default.KeyboardArrowUp
                                else Icons.Default.KeyboardArrowDown,
                                contentDescription = null
                            )
                        },
                        enabled = !isSubmitting,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Box(
                        Modifier
                            .matchParentSize()
                            .clickable(
                                enabled = !isSubmitting,
                                interactionSource = interaction,
                                indication = null
                            ) { categoryMenuExpanded = !categoryMenuExpanded }
                    )

                    DropdownMenu(
                        expanded = categoryMenuExpanded,
                        onDismissRequest = { categoryMenuExpanded = false },
                        modifier = Modifier.width(with(density) { categoryFieldWidthPx.toDp() })
                    ) {
                        writableBoards.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(displayNameOf(item)) },
                                onClick = {
                                    selectedName = item.name
                                    categoryMenuExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            item {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it.take(titleMax) },
                    label = { Text("제목", color = MaterialTheme.colorScheme.surface) },
                    singleLine = true,
                    isError = titleError,
                    enabled = !isSubmitting,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    supportingText = {
                        Row(Modifier.fillMaxWidth()) {
                            if (titleError) {
                                Text("제목을 입력해 주세요.", color = MaterialTheme.colorScheme.error)
                            }
                            Spacer(Modifier.weight(1f))
                            Text(
                                "${title.length} / $titleMax",
                                color = MaterialTheme.colorScheme.surface
                            )
                        }
                    },
                    trailingIcon = {
                        if (title.isNotEmpty()) {
                            IconButton(onClick = { title = "" }) {
                                Icon(Icons.Default.Close, contentDescription = "제목 지우기")
                            }
                        }
                    }
                )
            }

            item {
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it.take(contentMax) },
                    label = { Text("내용", color = MaterialTheme.colorScheme.surface) },
                    isError = contentError,
                    enabled = !isSubmitting,
                    supportingText = {
                        Row(Modifier.fillMaxWidth()) {
                            if (contentError) {
                                Text(
                                    "내용을 입력해 주세요.",
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                            Spacer(Modifier.weight(1f))
                            Text(
                                "${content.length} / $contentMax",
                                color = MaterialTheme.colorScheme.surface
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            if (isValid) onSubmit(selected, title.trim(), content.trim())
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 480.dp),
                    maxLines = 12
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    focusManager.clearFocus()
                    if (isValid) onSubmit(selected, title.trim(), content.trim())
                },
                enabled = isValid,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                Text(
                    text = if (isSubmitting) "등록 중…" else "등록하기",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

//TODO :: 나중에 공통 유틸로 빼놓자.
fun displayNameOf(category: BoardCategoryEnum): String = when (category) {
    BoardCategoryEnum.POPULAR -> "인기글"
    BoardCategoryEnum.FREE -> "자유게시판"
    BoardCategoryEnum.QNA -> "질문게시판"
}

@Preview(showBackground = true)
@Composable
private fun PostCreateScreenPreview() {
    AlbaTimeTheme {
        PostCreateScreen(
            padding = PaddingValues(),
            popBackStack = {},
            onSubmit = { _, _, _ -> }
        )
    }
}
