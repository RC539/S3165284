package uk.ac.tees.mad.projecthub.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navController: NavHostController) {
    Text(text = "HomeScreen", fontSize = 50.sp)
}