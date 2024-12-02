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
     private val authentication : FirebaseAuth
     ): ViewModel()
{ val isUserSignedIn = mutableStateOf(false)
     init {
          if(authentication.currentUser != null){
               isUserSignedIn.value = true
          }
     }
     fun signUp(context: Context, email: String, password : String){

     }
}