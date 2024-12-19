package uk.ac.tees.mad.projecthub.viewmodels

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uk.ac.tees.mad.projecthub.data.model.ProjectModel
import uk.ac.tees.mad.projecthub.data.room.ProjectData
import uk.ac.tees.mad.projecthub.repository.ProjectRepository
import uk.ac.tees.mad.projecthub.repository.UserRepository
import java.util.HashMap
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val projectRepository: ProjectRepository,
) : ViewModel() {

    val loading = mutableStateOf(false)
    val projects = mutableStateOf<List<ProjectModel>?>(null)
    val offlineProjects = mutableStateOf<List<ProjectData>?>(null)
    init {
        fetchProjects()
    }

    private fun fetchProjects() {
        viewModelScope.launch {
            projects.value = projectRepository.fetchProjects()
            Log.d("Projects", "Projects: ${projects.value}")
        }
    }

    fun putIntoDatabase(project : ProjectModel){
        viewModelScope.launch {
            val response = project.toProjectData()
            projectRepository.insertIntoDatabase(response)
            getAllFromDB()
        }
    }
    
    private fun getAllFromDB(){
        viewModelScope.launch {
            offlineProjects.value = projectRepository.getAllFromDatabase()
            Log.d("Offline Projects", "Offline Projects: ${offlineProjects.value}")
        }
    }

    fun ProjectModel.toProjectData():ProjectData{
        return ProjectData(
            projectName = this.projectName,
            projectDescription = this.projectDescription,
            requiredSkills = this.requiredSkills,
            deadline = this.deadline,
            budget = this.budget,
            imageUrl = this.imageUrl
        )
    }

    fun addProject(
        projectName: String,
        projectDescription: String,
        requiredSkills: String,
        deadline: String,
        budget: String,
        value: Uri?,
        context : Context
    ) {
        loading.value = true
        viewModelScope.launch {
            var imageUrl = projectRepository.uploadProjectImage(value!!)
            Log.d("Image URL", "Image URL: $imageUrl")
            projectRepository.uploadProjectData(
                projectName,
                projectDescription,
                requiredSkills,
                deadline,
                budget,
                imageUrl,
                context
            )
            fetchProjects()
            loading.value = false
        }
    }
}
