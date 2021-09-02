package com.claudiodegio.timenavigator.compose

import android.R
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource

class TimeDialogState {

    internal var visible by mutableStateOf(false)
    internal var value : TimeMode by mutableStateOf(TimeMode.ALL)

    fun show(timeMode:TimeMode){
        visible = true
        value = timeMode
    }
}


@Composable
fun TimeModeSelectDialog(state:TimeDialogState){
    if(state.visible) {
        AlertDialog( title = {
                Text(text = "stringResource(id = titleId)")


            },
            text = {
                Text(text = "stringResource(id = messageId)")
            },
            onDismissRequest = {
                state.visible = false
            },
            dismissButton = {
                TextButton(
                    content = { Text(text = stringResource(id = R.string.cancel).uppercase()) },
                    onClick = {
                        state.visible = false
                    })
            },
            confirmButton = {

            }
        )
    }

}