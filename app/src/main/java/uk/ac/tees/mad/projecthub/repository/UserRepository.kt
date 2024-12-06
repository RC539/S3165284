package uk.ac.tees.mad.projecthub.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {
    fun isUserSignedIn(): Boolean {
        return auth.currentUser != null
    }

    fun createUserOnFirestore(uid: String?, name: String, email: String, password: String, onResult: (Boolean) -> Unit) {
        if (uid != null) {
            val userMap = HashMap<String, Any>().apply {
                put("uid", uid)
                put("name", name)
                put("email", email)
                put("password", password)
            }
            firestore.collection("user").document(uid).set(userMap)
                .addOnSuccessListener { onResult(true) }
                .addOnFailureListener { onResult(false) }
        } else {
            onResult(false)
        }
    }

    fun signUp(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                onResult(true, authResult.user?.uid)
            }
            .addOnFailureListener { exception ->
                onResult(false, exception.message)
            }
    }

    fun signIn(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onResult(true, null)
            }
            .addOnFailureListener { exception ->
                onResult(false, exception.message)
            }
    }
}
