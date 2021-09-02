package com.example.timenavigatorcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.claudiodegio.timenavigator.compose.TimeMode
import com.claudiodegio.timenavigator.compose.TimeNavigator
import com.example.timenavigatorcompose.ui.theme.TimeNavigatorComposeTheme
import org.joda.time.LocalDate
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import org.joda.time.LocalDateTime
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimeNavigatorComposeTheme {
                Greeting()
            }
        }
    }
}

@Composable
fun Greeting() {

    Surface(color = MaterialTheme.colors.background) {
        Column {
            TopAppBar(
                title = { Text(text = "Sample") }
            )

            TimeNavigatorTester()
        }
    }
}

@Composable
private fun TimeNavigatorTester(date: LocalDate = LocalDate.now()){
    var dateDay by remember { mutableStateOf( LocalDateTime.now()) }

    var dateStartAsString by remember { mutableStateOf("") }
    var dateEndAsString by remember { mutableStateOf("") }
    var timeMode by remember { mutableStateOf(TimeMode.DAY) }

    Box(modifier = Modifier
        .height(3.dp)
        .background(Color.Red)
        .fillMaxWidth())
    Text(text = "$timeMode")
    TimeNavigator(timeMode, date = dateDay, onValueChange = { date, dateStart, dateEnd ->
        dateDay = date
        dateStartAsString = dateStart.toString()
        dateEndAsString = dateEnd.toString()
    }, onTimeModeChange = {
        timeMode = it
    })

    Text(text = "DS: $dateStartAsString")
    Text(text = "DE: $dateEndAsString")

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TimeNavigatorComposeTheme {
        Greeting()
    }
}