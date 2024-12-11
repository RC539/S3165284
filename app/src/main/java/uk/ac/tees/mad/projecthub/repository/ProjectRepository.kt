package uk.ac.tees.mad.projecthub.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import uk.ac.tees.mad.projecthub.data.model.ProjectModel
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ProjectRepository @Inject constructor(
    private val firebaseStorage: FirebaseStorage,
    private val firestore: FirebaseFirestore,
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
}