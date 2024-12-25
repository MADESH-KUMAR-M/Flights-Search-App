package com.example.flights.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flights.AppViewModelProvider
import com.example.flights.R
import com.example.flights.data.airport
import com.example.flights.data.flightData
import com.example.flights.ui.theme.FlightsTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: FlightViewModel = viewModel(factory = AppViewModelProvider.factory)
){
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.app_name),
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                        )
                        },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ){ innerPadding ->

        val uiState by viewModel.uiState.collectAsState()

        val focusRequester = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current

        Column (
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp, 0.dp)
        ) {
            Search(viewModel, focusRequester)
            if (uiState.isSelected){
                val firstFlight = uiState.flightList.getOrNull(0)
                val departure = firstFlight?.depart ?: "No Flights"

                Text(
                    "Flight from $departure",
                    fontWeight = FontWeight.Bold
                    )
                LazyColumn {
                    items(uiState.flightList) { flight ->
                        FlightCard(flight = flight)
                    }
                }
            }else {
                if (uiState.searchList.isEmpty()){
                    Text(
                        "Favorites",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp)
                    )
                    LazyColumn {
                        items(uiState.flightList) { flight ->
                            FlightCard(flight = flight)
                        }
                    }
                }else{
                    Suggestion(uiState.searchList, viewModel , focusManager)
                }
            }
        }
        }
}

@Composable
fun Search(viewModel: FlightViewModel, focusRequester: FocusRequester) {
    var text by remember { mutableStateOf(TextFieldValue()) }
    var isFocused by remember { mutableStateOf(false) }

    OutlinedTextField(
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        },
        value = text,
        onValueChange = {
            text = it
            viewModel.fetchAirports(text.text)
            viewModel.updateIsSelected(false)
                        },
        label = {
            if(!isFocused){
                Text("Enter Airport Name")
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.inversePrimary,
            unfocusedContainerColor = MaterialTheme.colorScheme.inversePrimary
        ),
        maxLines = 1,
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged {
                isFocused = it.isFocused
            }
            .padding(16.dp)
            .focusRequester(focusRequester),
        shape = CircleShape
    )
}


@Composable
fun Suggestion(
    suggest: List<airport>,
    viewModel: FlightViewModel,
    focusManager: FocusManager
) {
    LazyColumn {
        items(suggest) { value ->
            Row (
                modifier = Modifier
                    .padding(12.dp, 0.dp)
                    .clickable {
                        focusManager.clearFocus()
                        viewModel.updateIsSelected(true)
                        viewModel.fetchAllFlights(value.iata, value.name)
                    }
            ) {
                Text(
                    text = value.iata,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.width(50.dp)
                )
                Text(
                    text = value.name,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

    }
}


@Composable
fun FlightCard(
    viewModel: FlightViewModel = viewModel(),
    flight: flightData
){
    Card(
        modifier = Modifier.clip(RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.secondaryContainer),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(0.8f)
                    .clip(shape = RoundedCornerShape(16.dp))
                    .padding(8.dp)
            ) {
                Text("Depart")
                Row {
                    Text(
                        text = flight.depart_code,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.width(50.dp)
                    )
                    Text(flight.depart)
                }
                Text("Arrival")
                Row {
                    Text(
                        text = flight.arrival_code,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.width(50.dp)
                    )
                    Text(flight.arrival)
                }
            }
            if (flight.isFavorite == 1) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Favorite",
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.20f)
                        .clickable {
                            viewModel.deleteFavorite(flight.depart_code, flight.arrival_code)
                        },
                    tint = Color(255, 215, 0)
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Favorite",
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.20f)
                        .clickable {
                            viewModel.saveFavorite(flight.depart_code, flight.arrival_code)
                        },
                    tint = Color.Gray
                )
            }
        }
    }

}

@Composable
fun SuggestionPreview(){
    FlightsTheme {
        val mockItems = listOf(
            airport(
                iata = "JTC",
                name = "Jolarpattai",
                passengers = 10,
                id = 1
            ),
            airport(
                iata = "ED",
                name = "Erode",
                passengers = 10,
                id = 2
            ),
            airport(
                iata = "SlM",
                name = "Salem",
                passengers = 5,
                id = 3
            )
        )
    }
}

@Preview
@Composable
fun CardPreview(){
FlightsTheme {
    FlightCard(
        flight = flightData(
            arrival_code = "ED",
            arrival = "ERODE",
            depart_code = "JTC",
            depart = "Jolarpet",
            isFavorite = 0
        )
    )
}
}

@Preview
@Composable
fun HomeScreenPReview(){
    FlightsTheme {
        HomeScreen()
    }
}