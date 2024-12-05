package uk.ac.tees.mad.projecthub.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uk.ac.tees.mad.projecthub.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val loading = mutableStateOf(false)
    val isUserSignedIn = mutableStateOf(false)

    init {
        isUserSignedIn.value = userRepository.isUserSignedIn()
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
}
