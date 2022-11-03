package com.sakist_j.ageinminutes_compose

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sakist_j.ageinminutes_compose.ui.theme.AgeInMinutes_ComposeTheme
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AgeInMinutes_ComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.primary
                ) {
                    MainContent()
                }
                /*
                //create appbar with title only
                TopAppBar(title = { Text(text = "Age In Minutes App") },
                backgroundColor = MaterialTheme.colors.secondaryVariant)
*/
            }
        }
    }
}

@Composable
// Header UI
fun MainContent() {
    // logic section
    val context = LocalContext.current
    val myCalendar = Calendar.getInstance()
    val year = myCalendar.get(Calendar.YEAR)
    val month = myCalendar.get(Calendar.MONTH)
    val date = myCalendar.get(Calendar.DAY_OF_MONTH)
    // call SimpleDateFormat to get current date
    // https://developer.android.com/reference/kotlin/java/text/SimpleDateFormat
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
    val currentDate = sdf.format(System.currentTimeMillis())
    // save state of string --> mutableState<String!>
    val mDate = remember { mutableStateOf(currentDate) }

    val myDatePicker = DatePickerDialog(context,
        {_, years, months, dates ->
            mDate.value = "$dates/${months + 1}/$years"

        },
        year,
        month,
        date,
        )
    val selectedDate = sdf.parse(mDate.value)
    val selectedDateInMinutes = selectedDate!!.time / 60000
    //parse currentDate from String to Date for getting time
    val currentDateInMinutes = sdf.parse(currentDate)!!.time / 60000
    //calculate the difference between your life time in minutes and current time in minutes
    val differentTimeInMinutes = currentDateInMinutes - selectedDateInMinutes
    // not allow user to select day after the current day
    myDatePicker.datePicker.maxDate = System.currentTimeMillis() - 86400000
    //UI Section
    Column(verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier= Modifier.padding(12.dp)) {

        // Header UI
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier= Modifier.padding(5.dp)){
            Row {
                Text("Calculate Your".uppercase(),
                    style = MaterialTheme.typography.h1)
            }
            Row {
                Text("age".uppercase(),
                    style = MaterialTheme.typography.h1)
            }
            Row {
                Text("in minutes".uppercase(),
                    style = MaterialTheme.typography.h1)
            }
        }

        // Calculate Button
        Button(onClick = {
            /*callback function*/
            myDatePicker.show()
        },
            modifier = Modifier.width(250.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor =
            MaterialTheme.colors.secondaryVariant),
            elevation = ButtonDefaults.elevation()){
            Text("select date".uppercase(),
                color = MaterialTheme.colors.primaryVariant,
                style = MaterialTheme.typography.button)
        }

        //Selected Date UI
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier= Modifier.padding(5.dp)){
            Row {
                Text(mDate.value,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.primaryVariant)
            }
            Row {
                Text("Selected Date",style = MaterialTheme.typography.body1)
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier= Modifier.padding(5.dp)){
            Row{
                Text("$differentTimeInMinutes",
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.primaryVariant)
            }
            Row{
                Text("Your Age In Minutes",style = MaterialTheme.typography.body1)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AgeInMinutes_ComposeTheme {
        MainContent()
    }
}