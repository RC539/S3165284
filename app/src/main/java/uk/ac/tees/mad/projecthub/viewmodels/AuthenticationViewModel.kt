package uk.ac.tees.mad.projecthub.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uk.ac.tees.mad.projecthub.data.model.UserModel
import uk.ac.tees.mad.projecthub.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val loading = mutableStateOf(false)
    val isUserSignedIn = mutableStateOf(false)

    val userData = mutableStateOf<UserModel?> (null)

    init {
        isUserSignedIn.value = userRepository.isUserSignedIn()
        fetchUserData()
    }

    fun signUp(name: String, email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        loading.value = true
        userRepository.signUp(email, password) { success, uid ->
            loading.value = false
            if (success && uid != null) {
                userRepository.createUserOnFirestore(uid, name, email, password) { firestoreSuccess ->
                    if (firestoreSuccess) {
                        onResult(true, null)
                        isUserSignedIn.value = true
                    } else {
                        onResult(false, "Error creating user in Firestore")
                    }
                }
            } else {
                onResult(false, "Error signing up")
            }
        }
    }

    fun logIn(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        loading.value = true
        userRepository.signIn(email, password) { success, errorMessage ->
            loading.value = false
            if (success) {
                onResult(true, null)
                isUserSignedIn.value = true
            } else {
                onResult(false, errorMessage)
            }
        }
    }

    fun fetchUserData() {
        val uid = userRepository.returnUid()
        if (uid != null) {
            viewModelScope.launch {
                val response = userRepository.fetchUserData(uid)
                userData.value = response
                Log.d("UserData", "User data fetched: $userData")
            }
        }
    }
}
