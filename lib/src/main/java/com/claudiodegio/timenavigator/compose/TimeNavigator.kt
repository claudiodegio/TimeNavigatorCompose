package com.claudiodegio.timenavigator.compose

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
                  date: LocalDateTime,
                  backgroundColor: Color = MaterialTheme.colors.primary,
                  tintColor: Color = MaterialTheme.colors.onPrimary,
                  onValueChange: (LocalDateTime, LocalDateTime, LocalDateTime) -> Unit) {


    Row(modifier = Modifier
        .fillMaxWidth()
        .height(48.dp)
        .background(backgroundColor),
        verticalAlignment = Alignment.CenterVertically) {

        // Left
        Icon(modifier = Modifier
            .size(36.dp)
            .clickable {
                calculateDate(timeMode, date, -1).let {
                    onValueChange(it.first, it.second, it.third)
                }

            },
            painter =  painterResource(id = R.drawable.ic_round_chevron_left_24),
                contentDescription = "Arrow Left",
                tint = tintColor)

        // TODO
        Text(text = "$timeMode", color = tintColor)
        Text(text = formatDate(timeMode, date), color = tintColor)

        // Mode
        Icon(painter = painterResource(id = R.drawable.ic_round_expand_more_24),
            contentDescription = "Arrow Left",
            tint = tintColor)

        // Right
        Icon(modifier = Modifier
            .size(36.dp)
            .clickable {
                calculateDate(timeMode, date, 1).let {
                    onValueChange(it.first, it.second, it.third)
                }
            },
            painter =  painterResource(id = R.drawable.ic_round_chevron_right_24),            contentDescription = "Arrow Left",
            tint = tintColor)
    }

    LaunchedEffect(timeMode) {
        calculateDate(timeMode, date, 0).let {
            onValueChange(it.first, it.second, it.third)
        }
    }

}

private fun formatDate(timeMode: TimeMode, date: LocalDateTime) : String {
    return date.toString()
}

private fun calculateDate(timeMode: TimeMode, date: LocalDateTime, toSet:Int) : Triple<LocalDateTime, LocalDateTime, LocalDateTime> {
   val newDate = when(timeMode) {
        TimeMode.DAY -> date.plusDays(toSet)
        TimeMode.MONTH -> date.plusMonths(toSet)
        TimeMode.WEEK -> date.plusWeeks(toSet)
        TimeMode.YEAR -> date.plusYears(toSet)
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

    }
    return Triple(newDate, startDate, endDate)
}

@Preview(showBackground = true)
@Composable
private fun TimeNavigatorPreviewDay() {
    MaterialTheme {
        TimeNavigator(TimeMode.DAY, LocalDateTime.now(), onValueChange = { _, _, _ ->})
    }
}

@Preview(showBackground = true)
@Composable
private fun TimeNavigatorPreviewMonth() {
    MaterialTheme {
        TimeNavigator(TimeMode.MONTH, LocalDateTime.now(), onValueChange = {_, _, _ ->})
    }
}


@Preview(showBackground = true)
@Composable
private fun TimeNavigatorPreviewWeek() {
    MaterialTheme {
        TimeNavigator(TimeMode.WEEK, LocalDateTime.now(), onValueChange = {_, _, _ ->})
    }
}
@Preview(showBackground = true)
@Composable
private fun TimeNavigatorPreviewYear() {
    MaterialTheme {
        TimeNavigator(TimeMode.YEAR, LocalDateTime.now(), onValueChange = {_, _, _ ->})
    }
}