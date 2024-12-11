package uk.ac.tees.mad.projecthub.viewmodels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import uk.ac.tees.mad.projecthub.repository.ProjectRepository
import uk.ac.tees.mad.projecthub.repository.UserRepository
import java.util.HashMap
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val projectRepository: ProjectRepository,
) : ViewModel() {


    fun addProject(
        projectName: String,
        projectDescription: String,
        requiredSkills: String,
        deadline: String,
        budget: String,
        value: Uri?,
        context : Context
    ) {
        var imageUrl = ""
        projectRepository.uploadProjectImage(value!!, onSuccess = { imageUrl = it })
        projectRepository.uploadProjectData(
            projectName,
            projectDescription,
            requiredSkills,
            deadline,
            budget,
            imageUrl,
            context
        )
    }
}