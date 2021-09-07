package com.claudiodegio.timenavigator.compose

import android.text.format.DateFormat
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import java.util.*

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun TimeNavigator(timeMode: TimeMode,
                  timeModeSupported:List<TimeMode> = TimeMode.values().asList(),
                  date: LocalDateTime,
                  rowModifier: Modifier = Modifier,
                  backgroundColor: Color = MaterialTheme.colors.primary,
                  tintColor: Color = MaterialTheme.colors.onPrimary,
                  onValueChange: (TimeMode, LocalDateTime, LocalDateTime, LocalDateTime) -> Unit,
                  onTimeModeChange: (TimeMode) -> Unit = {}) {

    val actionEnabled = timeMode != TimeMode.ALL
    val tintColorAction = if (actionEnabled) tintColor else tintColor.copy(alpha = 0.65f)

    val timeDialogState = TimeDialogState()

    Row(modifier = rowModifier
        .fillMaxWidth()
        .background(backgroundColor),
        verticalAlignment = Alignment.CenterVertically) {

        // Left
        IconButton(enabled = actionEnabled,
            content = {
                Icon(
                    painter =  painterResource(id = R.drawable.ic_round_chevron_left_24),
                    tint = tintColorAction,
                    contentDescription = "Arrow Left")

             }, onClick = {
                calculateDate(timeMode, date, -1).let {
                    onValueChange(timeMode, it.first, it.second, it.third)
                }
            })


        // Spazio di rimenpimento
        Spacer(modifier = Modifier.weight(1f))

        // Icona per indicare la modalità di funzionamento
        TimeModeIndicator(modifier = Modifier.padding(end = 8.dp),
                            timeMode = timeMode,
                            tintColor = tintColor,
                            textColor = backgroundColor)

        Text(text = formatDate(timeMode, date), color = tintColor)

        // Select Mode
        if (timeModeSupported.size > 1) {
            IconButton(
                content = {
                    Icon(
                        painter = if (!timeDialogState.visible) painterResource(id = R.drawable.ic_round_expand_more_24) else painterResource(id = R.drawable.ic_round_expand_less_24),
                        tint = tintColor,
                        contentDescription = "Select mode")

                }, onClick = {
                    timeDialogState.show(timeMode)
                })
        }

        // Spazio di rimenpimento
        Spacer(modifier = Modifier.weight(1f))

        // Right
        IconButton(enabled = actionEnabled,
            content = {
                Icon(
                    painter =  painterResource(id = R.drawable.ic_round_chevron_right_24),
                    tint = tintColorAction,
                    contentDescription = "Arrow right")

            }, onClick = {
                calculateDate(timeMode, date, 1).let {
                    onValueChange(timeMode, it.first, it.second, it.third)
                }
            })
    }


    TimeModeSelectDialog(state = timeDialogState,
            timeModeSupported = timeModeSupported,
            onTimeModeChange = onTimeModeChange)

    LaunchedEffect(timeMode) {
        calculateDate(timeMode, date, 0).let {
            onValueChange(timeMode, it.first, it.second, it.third)
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
private fun TimeNavigatorPreviewDay() {
    MaterialTheme {
        Column {
            TimeNavigator(TimeMode.DAY, date = LocalDateTime.now(), onValueChange = { _, _, _, _ ->})

            TimeNavigator(TimeMode.MONTH, date =LocalDateTime.now(), onValueChange = {_, _, _, _ ->})

            TimeNavigator(TimeMode.WEEK, date = LocalDateTime.now(), onValueChange = {_, _, _, _ ->})

            TimeNavigator(TimeMode.YEAR, date = LocalDateTime.now(), onValueChange = {_, _, _, _ ->})

            TimeNavigator(TimeMode.ALL, date =LocalDateTime.now(), onValueChange = {_, _, _, _ ->})

            TimeNavigator(TimeMode.DAY, timeModeSupported = listOf(TimeMode.DAY), date = LocalDateTime.now(), onValueChange = { _, _, _, _ ->})
        }
    }
}