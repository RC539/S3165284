package uk.ac.tees.mad.projecthub.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import uk.ac.tees.mad.projecthub.data.model.ProjectModel
import uk.ac.tees.mad.projecthub.data.room.ProjectDao
import uk.ac.tees.mad.projecthub.data.room.ProjectData
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ProjectRepository @Inject constructor(
    private val firebaseStorage: FirebaseStorage,
    private val firestore: FirebaseFirestore,
    private val projectDao: ProjectDao
) {
    suspend fun uploadProjectImage(imageUri: Uri): String {
        return suspendCoroutine { continuation ->
            val storageRef = firebaseStorage.reference
            val imageRef = storageRef.child("project_images/${imageUri.lastPathSegment}")
            val uploadTask = imageRef.putFile(imageUri)

            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    continuation.resume(uri.toString())
                }.addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
            }.addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
        }
    }

    fun uploadProjectData(
        projectName: String,
        projectDescription: String,
        requiredSkills: String,
        deadline: String,
        budget: String,
        imageUrl: String,
        context : Context
    ) {
        val projectData = ProjectModel(
            projectName,
            projectDescription,
            requiredSkills,
            deadline,
            budget,
            imageUrl
        )
        firestore.collection("projects")
            .add(projectData)
            .addOnSuccessListener {
                Toast.makeText(context, "Project added successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
            }
    }

    suspend fun fetchProjects(): List<ProjectModel>? {
        return try {
            val snapshot = firestore.collection("projects").get().await()
            val projectList = snapshot.toObjects(ProjectModel::class.java)
            Log.d("Response", projectList.toString())
            projectList
        } catch (e: Exception) {
            Log.d("Error", e.message.toString())
            null
        }
    }

    suspend fun insertIntoDatabase(project : ProjectData){
        projectDao.insertProject(project)
    }

    suspend fun getAllFromDatabase() :List<ProjectData>{
        return projectDao.getAllProjects()
    }
}