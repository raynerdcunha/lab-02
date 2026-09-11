package com.example.listycity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity.ui.theme.ListyCityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val cityRepository = CityRepository()

        setContent {
            ListyCityTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CityListScreen(
                        cities = cityRepository.cities,
                        onAddCity = { cityRepository.addCity(it) },
                        onDeleteCity = { cityRepository.deleteCity(it) },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CityListScreen(
    // cities: List<String> is the list of city names that
    // this screen received from MainActivity
    cities: List<String>,
    onAddCity: (String) -> Unit,
    onDeleteCity: (String) -> Unit,
    // modifier: Modifier = Modifier allows layout information,
    // such as padding, to be passed into this screen
    modifier: Modifier = Modifier
) {
    var newCityName by remember {mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize()) {
        Row(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = newCityName,
                onValueChange = { newCityName = it },
                label = { Text("City name") },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    if (newCityName.isNotBlank()) {
                        onAddCity(newCityName)
                        newCityName = ""
                    }
                }
            ) {
                Text("Add City")
            }
        }

        // LazyColumn is the Compose for a basic scrolling ListView
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            // items(cities) loops through the city list and
            // creates one UI row for each city.
            items(cities) { city ->
                CityRow(city = city,
                    onDeleteCity = onDeleteCity
                )
            }
        }
    }
}

// The following function is from Anthropic, Claude, "How to add a delete key button onto the UI in the above code?", 2026-09-10
@Composable
fun CityRow(city: String, onDeleteCity: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = city,
            fontSize = 28.sp
        )
        IconButton(
            onClick = { onDeleteCity(city) }
        ) {
            Text(
                text = "X",
                fontSize = 20.sp,
                color = Color.Red
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ListyCityTheme {
        Greeting("Android")
    }
}

class CityRepository {
    private val _cities = mutableStateListOf(
        "Edmonton", "Vancouver", "Moscow",
        "Sydney", "Berlin", "Vienna",
        "Tokyo", "Beijing", "Osaka",
        "New Delhi"
    )

    //Get a read-only list for the UI display
    val cities: List<String>
        get() = _cities

    fun addCity(city: String) {
        _cities.add(city)
    }

    // Added a delete function to remove the city from the UI display
    // To-do of Lab-02
    fun deleteCity(city: String) {
        _cities.remove(city)
    }

}
