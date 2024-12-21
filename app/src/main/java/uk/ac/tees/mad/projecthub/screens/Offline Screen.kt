package uk.ac.tees.mad.projecthub.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import uk.ac.tees.mad.projecthub.data.room.ProjectData
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
            OfflineProjectList(projects = offlineProjects)
        }else{
            androidx.compose.material.Text(text = "No Projects Found")
        }
    }
}

@Composable
fun OfflineProjectList(projects: List<ProjectData>) {
    LazyColumn {
        items(projects){project->
            ProjectCardView(project = project)
        }
    }
}

@Composable
fun ProjectCardView(project: ProjectData){
    
}