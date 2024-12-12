package uk.ac.tees.mad.projecthub.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddBusiness
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import uk.ac.tees.mad.projecthub.data.model.ProjectModel
import uk.ac.tees.mad.projecthub.navigation.NavigationDestination
import uk.ac.tees.mad.projecthub.ui.theme.poppins
import uk.ac.tees.mad.projecthub.viewmodels.MainViewModel


@Composable
fun HomeScreen(navController: NavHostController, mainVm: MainViewModel) {

    val projects = mainVm.projects

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
            projects.value?.let {
                LazyColumn {
                    items(projects.value!!) { items ->
                        ProjectView(items)
                    }
                }
            }
        }
    }
}

@Composable
fun ProjectView(item: ProjectModel) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp),
        elevation = CardDefaults.cardElevation(9.dp)) {
        Column {
            Row {
                AsyncImage(
                    model = item.imageUrl, contentDescription = null, modifier = Modifier
                        .size(160.dp)
                        .padding(8.dp)
                        .clip(
                            RoundedCornerShape(12.dp)
                        )
                        .clickable {

                        }
                        .shadow(elevation = 8.dp), contentScale = ContentScale.FillBounds
                )
                Column(modifier = Modifier.padding(vertical = 14.dp)) {
                    Text(
                        text = item.projectName,
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 22.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = item.projectDescription,
                        fontFamily = poppins
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Budget :${item.budget}",
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold
                    )

                }
            }
            Text(
                text = "End Time : ${item.deadline}",
                fontFamily = poppins,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 2.dp)
            )
        }
    }
}