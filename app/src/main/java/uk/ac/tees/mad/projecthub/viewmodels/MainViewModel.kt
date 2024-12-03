package uk.ac.tees.mad.projecthub.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.HashMap
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
    ):ViewModel() {

    fun createUserOnFirestore(uid: String?, name: String, email: String, password: String): Boolean {
        if (uid != null) {
            firestore.collection("user").document(uid).set(
                HashMap<String, Any>().apply {
                    put("uid", uid)
                    put("name", name)
                    put("email", email)
                    put("password", password)
                }
            ).addOnSuccessListener {
                return@addOnSuccessListener
            }.addOnFailureListener {
                return@addOnFailureListener
            }
        }
        return false
    }
}