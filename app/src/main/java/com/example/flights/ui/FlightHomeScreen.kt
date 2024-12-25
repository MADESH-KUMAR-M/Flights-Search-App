package com.example.flights.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flights.AppViewModelProvider
import com.example.flights.R
import com.example.flights.data.airport
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
        Column (
            modifier = Modifier.padding(innerPadding)
        ) {
            Search()
            Suggestion(uiState.searchList)
        }
        }
}

@Composable
fun Search (){
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
        onValueChange = { text = it },
        label = {
            if(!isFocused){
                Text("Enter Airport Name")
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.inversePrimary,
            unfocusedContainerColor = MaterialTheme.colorScheme.inversePrimary
        ),
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged {
                isFocused = it.isFocused
            }
            .padding(16.dp)
        ,
        shape = CircleShape
    )
}


@Composable
fun Suggestion(
    suggest: List<airport>
) {
    LazyColumn {
        items(suggest) { value ->
            Row (
                modifier = Modifier.padding(12.dp, 0.dp)
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

@Preview
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
        Suggestion(mockItems)
    }
}

@Preview
@Composable
fun HomeScreenPReview(){
    FlightsTheme {
        HomeScreen()
    }
}