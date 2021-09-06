# TimeNavigatorCompose

[![Release](https://jitpack.io/v/claudiodegio/TimeNavigatorCompose.svg)]
(https://jitpack.io/#claudiodegio/TimeNavigatorCompose)

Cute library to implement a simple time navigation view. *Works from Android API 21 (Lollipop) and above*.

It the upgraded library https://github.com/claudiodegio/TimeNavigatorCompose with compose


# Feature

- Use of jetpack compose
- Support for time navigation of year, month, week, day ad whole period
- Time mode configurable
- Time mode change integrated with dialog

![sample](https://raw.githubusercontent.com/claudiodegio/TimeNavigator/master/screen/screen_001.png)
![sample](https://raw.githubusercontent.com/claudiodegio/TimeNavigator/master/screen/screen_002.png)


# Usage
**Add the dependencies to your gradle file:**
```javascript
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
dependencies {
        compile 'com.github.claudiodegio:TimeNavigatorCompose:1.0.0'
}
```
**Add TimeNavigator to your layout file along with the Toolbar**

```kotlin

@ExperimentalMaterialApi
@Composable
@ExperimentalFoundationApi
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
    }, onValueChange = {
        timeMode = it
    })

    Text(text = "DS: $dateStartAsString")
    Text(text = "DE: $dateEndAsString")

}
```

| Param             | Description                             |
| ----------------- | --------------------------------------- |
| timeMode          | Time mode requested or selected by user |
| date              | Initial date                            |
| onValueChange     | Callback for time selection change      |
| onValueChange     |  Callback time mode changed             |

# Help me
Pull requests are more than welcome, help me and others improve this awesome library.

# License
	Copyright 2021 Claudio Degioanni

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.