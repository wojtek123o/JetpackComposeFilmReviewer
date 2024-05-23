package com.example.lab3comp.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun InfoScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text("Aplikacja Film Reviewer umożliwia użytkownikom przechowywanie personalnej listy filmów oraz dodawanie do każdego z nich recenzji i oceny. Użytkownicy mogą łatwo zarządzać swoimi ulubionymi filmami, dzięki funkcji dodawania nowych tytułów, edycji istniejących wpisów, a także oceniania i opisywania filmów. ")
    }
}