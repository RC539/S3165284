package uk.ac.tees.mad.projecthub.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddBusiness
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.ac.tees.mad.projecthub.navigation.NavigationDestination


@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Row(modifier = Modifier.fillMaxWidth()) {

            }
        })
    },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(NavigationDestination.AddProjectScreen.name) }) {
                Icon(imageVector = Icons.Rounded.AddBusiness, contentDescription = null)
            }
        }
    )
    {
        Column(modifier = Modifier.padding(it)) {

        }
    }
}