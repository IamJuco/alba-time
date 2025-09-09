package com.juco.feature.post.create

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juco.designsystem.theme.AlbaTimeTheme
import com.juco.designsystem.theme.Blue
import com.juco.designsystem.topbar.PreviousTopBar
import com.juco.feature.post.component.Separator

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
    onSubmit: (title: String, content: String) -> Unit = { _, _ -> }
) {
    val focusManager = LocalFocusManager.current

    var title by rememberSaveable { mutableStateOf("") }
    var content by rememberSaveable { mutableStateOf("") }

    val titleMax = 50
    val contentMax = 2_000

    val titleError = title.isBlank()
    val contentError = content.isBlank()
    val isValid = !titleError && !contentError && !isSubmitting

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
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it.take(titleMax) },
                    label = { Text("제목", color = MaterialTheme.colorScheme.surface) },
                    singleLine = true,
                    isError = titleError,
                    enabled = !isSubmitting,
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
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    modifier = Modifier.fillMaxWidth()
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
                                Text("내용을 입력해 주세요.", color = MaterialTheme.colorScheme.error)
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
                            if (isValid) {
                                focusManager.clearFocus()
                                onSubmit(title.trim(), content.trim())
                            } else {
                                focusManager.clearFocus()
                            }
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 480.dp),
                    maxLines = 12
                )
            }
        }

        Surface(
            tonalElevation = 2.dp,
            color = MaterialTheme.colorScheme.background,
            border = BorderStroke(width = 1.dp, color = Blue)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(Modifier.weight(1f))

                Button(
                    onClick = {
                        focusManager.clearFocus()
                        onSubmit(title.trim(), content.trim())
                    },
                    enabled = isValid,
                ) {
                    Text(if (isSubmitting) "등록 중…" else "등록하기")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PostCreateScreenPreview() {
    AlbaTimeTheme {
        PostCreateScreen(
            padding = PaddingValues(),
            popBackStack = {}
        )
    }
}