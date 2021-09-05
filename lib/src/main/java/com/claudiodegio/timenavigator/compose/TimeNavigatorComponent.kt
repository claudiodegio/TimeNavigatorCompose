package com.claudiodegio.timenavigator.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.joda.time.LocalDateTime

@Composable
internal fun TimeModeIndicator(timeMode: TimeMode,
                               modifier: Modifier = Modifier,
                               textColor: Color = MaterialTheme.colors.onPrimary,
                               tintColor: Color = MaterialTheme.colors.onPrimary) {
    val icon = when(timeMode) {
        TimeMode.ALL -> painterResource(id = R.drawable.ic_infinite_sign_24_dp)
        else -> null
    }

    icon?.let {
        Icon(modifier = modifier.padding(8.dp),
            painter =  it,
            contentDescription = "Mode icon",
            tint = tintColor)
    }

    val text = when(timeMode) {
        TimeMode.YEAR -> "365"
        TimeMode.MONTH -> "30"
        TimeMode.WEEK -> "7"
        TimeMode.DAY -> "1"
        else -> ""
    }

    if (text.isNotEmpty()) {
        Text(text = text,
            modifier = modifier
                .wrapContentWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(tintColor)
                .padding(horizontal = 4.dp, vertical = 2.dp),
            fontWeight = FontWeight.Bold, color = textColor)
    }
}



@Composable
internal fun formatDate(timeMode: TimeMode, date: LocalDateTime) : String {

    return when(timeMode) {
        TimeMode.DAY -> stringResource(id = R.string.period_day, date.toDate())
        TimeMode.MONTH -> stringResource(id = R.string.period_month, date.toDate())
        TimeMode.WEEK -> {
            // calcolo la data del range
            val range = calculateDate(timeMode, date, 0)

            if (range.second.monthOfYear != range.third.monthOfYear) {
                stringResource(id = R.string.period_week_diff_month, range.second.toDate(), range.third.toDate())
            } else {
                stringResource(id = R.string.period_week_same_month, range.second.toDate(), range.third.toDate())
            }

        }
        TimeMode.YEAR -> {
            stringResource(id = R.string.period_year, date.toDate())
        }
        else -> stringResource(id = R.string.period_all)
    }

}

internal fun calculateDate(timeMode: TimeMode, date: LocalDateTime, toSet:Int) : Triple<LocalDateTime, LocalDateTime, LocalDateTime> {
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