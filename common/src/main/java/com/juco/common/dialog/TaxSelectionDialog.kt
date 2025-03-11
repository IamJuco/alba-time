package com.juco.common.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juco.common.model.UiTaxType

@Composable
fun TaxSelectionDialog(
    selectedTax: UiTaxType,
    onDismiss: () -> Unit,
    onConfirm: (UiTaxType) -> Unit
) {
    val taxOptions = listOf(UiTaxType.NONE, UiTaxType.LOW, UiTaxType.HIGH)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("세금 설정") },
        text = {
            Column {
                taxOptions.forEach { taxType ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onConfirm(taxType) }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = taxType == selectedTax,
                            onClick = { onConfirm(taxType) }
                        )
                        Text(text = taxType.displayName, fontSize = 18.sp)
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("닫기")
            }
        }
    )
}