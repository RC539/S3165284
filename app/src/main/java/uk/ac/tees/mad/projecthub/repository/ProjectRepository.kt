package uk.ac.tees.mad.projecthub.repository

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject

class ProjectRepository @Inject constructor(
    private val firebaseStorage: FirebaseStorage,
    private val firestore: FirebaseFirestore
) {
    fun uploadProjectImage(imageUri : Uri, onSuccess:(String)-> Unit){
        val storageRef = firebaseStorage.reference
        val imageRef = storageRef.child("project_images/${imageUri.lastPathSegment}")
        val uploadTask = imageRef.putFile(imageUri)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener {
                onSuccess(imageRef.downloadUrl.toString())
            }
            }.addOnFailureListener{
            it.printStackTrace()
        }
    }
}