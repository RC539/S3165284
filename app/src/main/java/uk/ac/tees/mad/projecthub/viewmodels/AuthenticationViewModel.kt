package uk.ac.tees.mad.projecthub.viewmodels

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.default
import kotlinx.coroutines.launch
import uk.ac.tees.mad.projecthub.data.model.UserModel
import uk.ac.tees.mad.projecthub.repository.UserRepository
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
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
        if (isUserSignedIn.value) {
            fetchUserData()
        }
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

    fun updateProfilePicture(image: Uri, context: Context) {
        viewModelScope.launch {
            loading.value = true
            try {
                val fileFromUri = getFileFromUri(image, context)
                val compressedImageFile = Compressor.compress(context, fileFromUri) {
                    default()
                }
                userRepository.addProfilePhoto(Uri.fromFile(compressedImageFile))
                fetchUserData()
            } catch (e: Exception) {
                Log.d("Profile Picture", "Error: ${e.message}")
            }
            loading.value = false
        }
    }

    private fun getFileFromUri(uri: Uri, context: Context): File {
        val fileName = getFileName(uri, context) ?: "temp_image"
        val tempFile = File(context.cacheDir, fileName)

        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(tempFile)

        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        return tempFile
    }

    private fun getFileName(uri: Uri, context: Context): String? {
        var fileName: String? = null
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex >= 0) {
                    fileName = it.getString(nameIndex)
                }
            }
        }
        return fileName
    }

    fun updateUserData(name: String, email: String) {
        viewModelScope.launch {
            loading.value = true
            try {
                userRepository.editUserProfile(name, email)
                fetchUserData()
            } catch (e: Exception) {
                Log.d("User Data", "Error: ${e.message}")
            }
            loading.value = false
        }
    }

    fun logout() {
        userRepository.logout()
        isUserSignedIn.value = false
    }
}
