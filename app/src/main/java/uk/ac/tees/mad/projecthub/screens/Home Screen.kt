package uk.ac.tees.mad.projecthub.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddBusiness
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
import uk.ac.tees.mad.projecthub.navigation.buildProjectDetailRoute
import uk.ac.tees.mad.projecthub.ui.theme.poppins
import uk.ac.tees.mad.projecthub.viewmodels.MainViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, mainVm: MainViewModel) {
    val projects = mainVm.projects.value
    val loading by mainVm.loading


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                    ) {
                        Text(
                            text = "ProjectHub",
                            fontFamily = poppins,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 30.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Rounded.Person,
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(8.dp)
                                .size(32.dp)
                                .clickable {
                                    navController.navigate(NavigationDestination.ProfileScreen.name)
                                },
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                modifier = Modifier.height(80.dp),
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(NavigationDestination.AddProjectScreen.name) },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(imageVector = Icons.Rounded.AddBusiness, contentDescription = null)
            }
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            if (loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                if (!projects.isNullOrEmpty()) {
                    LazyColumn {
                        items(projects) { item ->
                            ProjectView(item, navigateToDetail = {
                                navigateToProjectDetailScreen(navController, item)
                            })
                        }
                    }
                } else {
                    Text(
                        text = "No projects available",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}


@Composable
fun ProjectView(item: ProjectModel, navigateToDetail : ()-> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable {
                navigateToDetail()
            },
        elevation = CardDefaults.cardElevation(9.dp)
    ) {
        Column {
            Row {
                AsyncImage(
                    model = item.imageUrl, contentDescription = null, modifier = Modifier
                        .size(120.dp)
                        .padding(8.dp)
                        .clip(
                            RoundedCornerShape(12.dp)
                        )
                        .clickable {

                        }
                        .shadow(elevation = 8.dp), contentScale = ContentScale.FillBounds
                )
                Column(modifier = Modifier.padding(vertical = 6.dp)) {
                    Text(
                        text = item.projectName,
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
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


fun navigateToProjectDetailScreen(navController: NavHostController, projectModel: ProjectModel) {
    val route = buildProjectDetailRoute(projectModel)
    navController.navigate(route)
}