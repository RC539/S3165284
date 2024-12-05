package uk.ac.tees.mad.projecthub.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import uk.ac.tees.mad.projecthub.repository.UserRepository
import java.util.HashMap
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun createUserOnFirestore(uid: String?, name: String, email: String, password: String) {
        userRepository.createUserOnFirestore(uid, name, email, password) { success ->

        }
    }
}