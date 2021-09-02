package com.claudiodegio.timenavigator.compose

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import java.util.*

@Composable
fun TimeNavigator(timeMode: TimeMode,
                  timeModeSupported:List<TimeMode> = TimeMode.values().asList(),
                  date: LocalDateTime,
                  backgroundColor: Color = MaterialTheme.colors.primary,
                  tintColor: Color = MaterialTheme.colors.onPrimary,
                  onValueChange: (LocalDateTime, LocalDateTime, LocalDateTime) -> Unit,
                  onTimeModeChange: (TimeMode) -> Unit = {}) {

    val actionEnabled = timeMode != TimeMode.ALL
    val tintColorAction = if (actionEnabled) tintColor else tintColor.copy(alpha = 0.65f)

    val timeDialogState = TimeDialogState()

    Row(modifier = Modifier
        .fillMaxWidth()
        .height(48.dp)
        .background(backgroundColor),
        verticalAlignment = Alignment.CenterVertically) {

        // Left
        IconButton(enabled = actionEnabled,
            content = {
                Icon(
                    modifier = Modifier.size(36.dp),
                    painter =  painterResource(id = R.drawable.ic_round_chevron_left_24),
                    tint = tintColorAction,
                    contentDescription = "Arrow Left")

             }, onClick = {
                calculateDate(timeMode, date, -1).let {
                    onValueChange(it.first, it.second, it.third)
                }
            })

        // TODO
        Text(text = "$timeMode ", color = tintColor)
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

        // Right
        IconButton(enabled = actionEnabled,
            content = {
                Icon(
                    modifier = Modifier.size(36.dp),
                    painter =  painterResource(id = R.drawable.ic_round_chevron_right_24),
                    tint = tintColorAction,
                    contentDescription = "Arrow right")

            }, onClick = {
                calculateDate(timeMode, date, 1).let {
                    onValueChange(it.first, it.second, it.third)
                }
            })
    }


    TimeModeSelectDialog(state = timeDialogState)

    LaunchedEffect(timeMode) {
        calculateDate(timeMode, date, 0).let {
            onValueChange(it.first, it.second, it.third)
        }
    }

}

private fun formatDate(timeMode: TimeMode, date: LocalDateTime) : String {
    return when(timeMode) {
        TimeMode.DAY -> date.toString("yyyy-MM-dd")
        TimeMode.MONTH -> date.toString("yyyy-MM")
        TimeMode.WEEK -> date.toString("yyyy-ww")
        TimeMode.YEAR -> date.toString("yyyy")
        else -> "ALL"
    }

}

private fun calculateDate(timeMode: TimeMode, date: LocalDateTime, toSet:Int) : Triple<LocalDateTime, LocalDateTime, LocalDateTime> {
   val newDate = when(timeMode) {
        TimeMode.DAY -> date.plusDays(toSet)
        TimeMode.MONTH -> date.plusMonths(toSet)
        TimeMode.WEEK -> date.plusWeeks(toSet)
        TimeMode.YEAR -> date.plusYears(toSet)
        TimeMode.ALL -> date
   }
    val startDate = when(timeMode) {
        TimeMode.DAY -> newDate
            .withHourOfDay(0)
            .withMinuteOfHour(0)
            .withSecondOfMinute(0)
        TimeMode.MONTH -> newDate
            .withDayOfMonth(1)
            .withHourOfDay(0)
            .withMinuteOfHour(0)
            .withSecondOfMinute(0)
        TimeMode.WEEK -> newDate
            .withDayOfWeek(1)
            .withHourOfDay(0)
            .withMinuteOfHour(0)
            .withSecondOfMinute(0)
        TimeMode.YEAR -> newDate
            .withDayOfYear(1)
            .withHourOfDay(0)
            .withMinuteOfHour(0)
            .withSecondOfMinute(0)
        else -> LocalDateTime.now().minusYears(1000)
    }

    val endDate = when(timeMode) {
        TimeMode.DAY -> newDate
            .withHourOfDay(23)
            .withMinuteOfHour(59)
            .withSecondOfMinute(59)
        TimeMode.MONTH -> newDate
            .withDayOfMonth(1)
            .plusMonths(1)
            .minusDays(1)
            .withHourOfDay(23)
            .withMinuteOfHour(59)
            .withSecondOfMinute(59)
        TimeMode.WEEK -> newDate
            .withDayOfWeek(7)
            .withHourOfDay(23)
            .withMinuteOfHour(59)
            .withSecondOfMinute(59)
        TimeMode.YEAR -> newDate
            .withDayOfYear(1)
            .plusYears(1)
            .minusDays(1)
            .withHourOfDay(23)
            .withMinuteOfHour(59)
            .withSecondOfMinute(59)
        else -> LocalDateTime.now().plusYears(1000)

    }
    return Triple(newDate, startDate, endDate)
}

@Preview(showBackground = true)
@Composable
private fun TimeNavigatorPreviewDay() {
    MaterialTheme {
        TimeNavigator(TimeMode.DAY, date = LocalDateTime.now(), onValueChange = { _, _, _ ->})
    }
}

@Preview(showBackground = true)
@Composable
private fun TimeNavigatorPreviewMonth() {
    MaterialTheme {
        TimeNavigator(TimeMode.MONTH, date =LocalDateTime.now(), onValueChange = {_, _, _ ->})
    }
}


@Preview(showBackground = true)
@Composable
private fun TimeNavigatorPreviewWeek() {
    MaterialTheme {
        TimeNavigator(TimeMode.WEEK, date = LocalDateTime.now(), onValueChange = {_, _, _ ->})
    }
}
@Preview(showBackground = true)
@Composable
private fun TimeNavigatorPreviewYear() {
    MaterialTheme {
        TimeNavigator(TimeMode.YEAR, date = LocalDateTime.now(), onValueChange = {_, _, _ ->})
    }
}

@Preview(showBackground = true)
@Composable
private fun TimeNavigatorPreviewALL() {
    MaterialTheme {
        TimeNavigator(TimeMode.ALL, date =LocalDateTime.now(), onValueChange = {_, _, _ ->})
    }
}

@Preview(showBackground = true)
@Composable
private fun TimeNavigatorPreviewOnlyOneModeL() {
    MaterialTheme {
        TimeNavigator(TimeMode.DAY, timeModeSupported = listOf(TimeMode.DAY), date = LocalDateTime.now(), onValueChange = { _, _, _ ->})
    }
}