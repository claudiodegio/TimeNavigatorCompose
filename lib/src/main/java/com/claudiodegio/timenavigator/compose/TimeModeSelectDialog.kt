package com.claudiodegio.timenavigator.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

internal class TimeDialogState {

    internal var visible by mutableStateOf(false)
    internal var selected : TimeMode by mutableStateOf(TimeMode.ALL)

    fun show(timeMode:TimeMode){
        visible = true
        selected = timeMode
    }
}


@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
internal fun TimeModeSelectDialog(state:TimeDialogState,
                         timeModeSupported:List<TimeMode>,
                         onTimeModeChange: (TimeMode) -> Unit = {}){
    if(state.visible) {
        AlertDialog(
            title = {
                Text(
                    text = stringResource(id = R.string.dialog_select_mode),
                    style = MaterialTheme.typography.h6)
            },
            text = {
                Text(text = "", fontSize = 12.sp, modifier = Modifier.height(8.dp))
                LazyColumn {
                    items(timeModeSupported) {
                        TimeModeItem(
                            timeMode = it,
                            state = state,
                            selected = it == state.selected,
                            onTimeModeChange = onTimeModeChange)
                    }
                }

            },
            onDismissRequest = {
                state.visible = false
            },
            buttons = {
            }
        )
    }
}


@ExperimentalMaterialApi
@Composable
private fun TimeModeItem(timeMode: TimeMode,
                            state:TimeDialogState,
                            selected: Boolean,
                            onTimeModeChange: (TimeMode) -> Unit = {}){


    val color = if (selected) MaterialTheme.colors.primary.copy(alpha = 0.2f) else MaterialTheme.colors.background
    ListItem(
        modifier = Modifier
            .background(color)
            .clickable {
                onTimeModeChange(timeMode)
                state.visible = false
            },
        icon = {
            Box(modifier = Modifier.size(40.dp)) {
                TimeModeIndicator(modifier = Modifier.align(Alignment.Center),
                    timeMode = timeMode,
                    tintColor = Color.Gray)
            }
        },
        text = {
            Text(text = timeModeToString(timeMode))
        }
    )
}

@Composable
private fun timeModeToString(timeMode: TimeMode) : String{
    return when(timeMode) {
        TimeMode.DAY -> stringResource(id = R.string.dialog_item_mode_day)
        TimeMode.MONTH -> stringResource(id = R.string.dialog_item_mode_month)
        TimeMode.YEAR -> stringResource(id = R.string.dialog_item_mode_year)
        TimeMode.WEEK -> stringResource(id = R.string.dialog_item_mode_week)
        TimeMode.ALL -> stringResource(id = R.string.dialog_item_mode_all_time)
    }
}

@ExperimentalMaterialApi
@Composable
@Preview(showBackground = true)
private fun TimeModeItemPreview(){

    Column() {
        TimeMode.values().forEach {
            TimeModeItem(it,TimeDialogState(), false)

        }
        TimeModeItem(TimeMode.DAY,TimeDialogState(), true)

    }
}
