package uk.ac.tees.mad.projecthub.screens

import android.Manifest
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import uk.ac.tees.mad.projecthub.R
import uk.ac.tees.mad.projecthub.navigation.NavigationDestination
import uk.ac.tees.mad.projecthub.ui.theme.poppins
import uk.ac.tees.mad.projecthub.viewmodels.AuthenticationViewModel

@Composable
fun ProfileScreen(userVm: AuthenticationViewModel , onToggle:()->Unit , navController: androidx.navigation.NavHostController) {
    val userData = userVm.userData
    val isLoading = remember { mutableStateOf(true) }
    val retryCount = remember { mutableStateOf(0) }
    val name = remember { mutableStateOf(userData.value?.name ?: "") }
    val email = remember { mutableStateOf(userData.value?.email ?: "") }
    val context = LocalContext.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    var tempImageUri: Uri? by remember { mutableStateOf(null) }
    val loading = userVm.loading.value
    val colorScheme = androidx.compose.material3.MaterialTheme.colorScheme

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            Toast.makeText(context, "Picture taken successfully!", Toast.LENGTH_SHORT).show()
            imageUri.value = tempImageUri
            Log.d("ProfileScreen", "Image URI: ${imageUri.value}")
            imageUri.value?.let { userVm.updateProfilePicture(it, context) }
        } else {
            Toast.makeText(context, "Picture taking failed!", Toast.LENGTH_SHORT).show()
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(context, "Camera permission granted", Toast.LENGTH_SHORT).show()
            tempImageUri = getImageUri(context)
            takePictureLauncher.launch(tempImageUri!!)

        } else {
            Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(key1 = retryCount.value) {
        userVm.fetchUserData()

        if (userData.value != null) {
            isLoading.value = false
        } else {
            delay(5000L)

            if (userData.value == null && retryCount.value < 1) {
                retryCount.value += 1
            } else {
                isLoading.value = false
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorScheme.background
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = Brush.verticalGradient(listOf(colorScheme.primary, colorScheme.secondary)))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 70.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(30.dp)
                    )
                    .clip(RoundedCornerShape(30.dp))
                    .background(colorScheme.surface)
            ) {
                if (isLoading.value) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = colorScheme.primary
                    )
                } else {
                    userData.value?.let { user ->
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.height(80.dp))
                            if (userData.value!!.userProfile.isNotEmpty()) {
                                AsyncImage(
                                    model = userData.value!!.userProfile,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(200.dp)
                                        .clip(CircleShape)
                                        .border(2.dp, colorScheme.onSurface, CircleShape)
                                        .clickable {
                                            permissionLauncher.launch(Manifest.permission.CAMERA)
                                        },
                                    contentScale = ContentScale.FillBounds
                                )
                            } else {
                                Image(
                                    painter = painterResource(id = R.drawable.man),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(200.dp)
                                        .clip(CircleShape)
                                        .border(2.dp, colorScheme.onSurface, CircleShape)
                                        .clickable {
                                            permissionLauncher.launch(Manifest.permission.CAMERA)
                                        })
                            }
                            Spacer(modifier = Modifier.height(40.dp))
                            OutlinedTextField(
                                value = name.value,
                                onValueChange = { name.value = it },
                                label = { Text("Name") },
                                colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = colorScheme.onSurface,
                                    focusedBorderColor = colorScheme.primary,
                                    unfocusedBorderColor = colorScheme.onSurface.copy(alpha = 0.5f)
                                ),
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            OutlinedTextField(
                                value = email.value,
                                onValueChange = { email.value = it },
                                label = { Text("Email") },
                                colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = colorScheme.onSurface,
                                    focusedBorderColor = colorScheme.primary,
                                    unfocusedBorderColor = colorScheme.onSurface.copy(alpha = 0.5f)
                                ),
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            Button(onClick = { onToggle() }) {
                                Text(
                                    text = "Toggle Dark/Light Mode",
                                    fontFamily = poppins,
                                    color = colorScheme.onPrimary,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Row {
                                Button(
                                    onClick = {
                                        userVm.updateUserData(name.value, email.value)
                                    },
                                    colors = ButtonDefaults.buttonColors(colorScheme.primary),
                                    shape = RoundedCornerShape(20.dp),
                                    modifier = Modifier.padding(30.dp)
                                ) {
                                    if (loading) {
                                        CircularProgressIndicator(color = colorScheme.onPrimary)
                                    } else {
                                        Text(
                                            text = "Save",
                                            fontFamily = poppins,
                                            color = colorScheme.onPrimary,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                Button(
                                    onClick = {
                                        userVm.logout()
                                        navController.navigate(NavigationDestination.LoginScreen.name){
                                            popUpTo(0)
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(colorScheme.error),
                                    shape = RoundedCornerShape(20.dp),
                                    modifier = Modifier.padding(30.dp)
                                ) {
                                    Text(
                                        text = "Log Out",
                                        fontFamily = poppins,
                                        color = colorScheme.onError,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                    } ?: run {
                        Text(
                            text = "No data",
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            style = MaterialTheme.typography.h6,
                            color = colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}
