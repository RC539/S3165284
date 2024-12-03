package uk.ac.tees.mad.projecthub.viewmodels

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel
     @Inject constructor(
     private val authentication : FirebaseAuth,
     ): ViewModel()
{
          val loading = mutableStateOf(false)
          val isUserSignedIn = mutableStateOf(false)
     init {
          if(authentication.currentUser != null){
               isUserSignedIn.value = true
          }
     }
     fun signUp(name: String, email: String, password: String, onResult: (Boolean, String?) -> Unit) {
          loading.value = true
          authentication.createUserWithEmailAndPassword(email, password)
               .addOnSuccessListener { authResult ->
                    val uid = authResult.user?.uid
                    if (uid != null) {
                        // mainViewModel.createUserOnFirestore(uid, name, email, password)
                         onResult(true, null)
                         isUserSignedIn.value = true
                    } else {
                         onResult(false, "User ID not found")
                    }
               }
               .addOnFailureListener { exception ->
                    loading.value = false
                    onResult(false, exception.message)
               }

     }

     fun logIn(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
          loading.value = true
          authentication.signInWithEmailAndPassword(email, password).addOnSuccessListener {
               onResult(true, null)
               isUserSignedIn.value = true
          }.addOnFailureListener { exception ->
               loading.value = false
               onResult(false, exception.message)
          }
     }
}