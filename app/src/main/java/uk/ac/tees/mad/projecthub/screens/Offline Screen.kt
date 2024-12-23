package uk.ac.tees.mad.projecthub.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import uk.ac.tees.mad.projecthub.data.room.ProjectData
import uk.ac.tees.mad.projecthub.ui.theme.poppins
import uk.ac.tees.mad.projecthub.viewmodels.MainViewModel

@Composable
fun OfflineScreen(mainvm: MainViewModel, navController: NavHostController) {
    mainvm.getAllFromDB()
    val offlineProjects = mainvm.offlineProjects.value
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if (offlineProjects != null) {
            OfflineProjectList(navController = navController, projects = offlineProjects)
        }else{
            androidx.compose.material.Text(text = "No Projects Found")
        }
    }
}

@Composable
fun OfflineProjectList(navController: NavHostController, projects: List<ProjectData>) {
    Scaffold(modifier = Modifier.statusBarsPadding(),topBar ={
        TopAppBar(title = {
            Row(modifier = Modifier.fillMaxWidth()) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null, modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable { navController.popBackStack() })
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Saved Projects",
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        })
    }) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            items(projects) { project ->
                ProjectCardView(project = project)
            }
        }
    }
}

@Composable
fun ProjectCardView(project: ProjectData){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Column {
            Row {
                AsyncImage(model = project.imageUrl, contentDescription = null,
                    modifier = Modifier.size(180.dp).clip(RoundedCornerShape(12.dp)), contentScale = androidx.compose.ui.layout.ContentScale.Crop)
                Column {
                    Text(text = project.projectName, fontFamily = poppins, fontWeight = FontWeight.Bold)
                    Text(text = project.projectDescription, fontFamily = poppins, fontWeight = FontWeight.Normal)
                    Text(text = "Required Skills :- ${project.requiredSkills}", fontFamily = poppins, fontWeight = FontWeight.SemiBold)
                }
            }
            Text(text = "Project Budget :- ${project.budget}", fontFamily = poppins, fontWeight = FontWeight.SemiBold)
            Text(text = "Project Deadline :- ${project.deadline}", fontFamily = poppins, fontWeight = FontWeight.SemiBold)
        }
    }
}