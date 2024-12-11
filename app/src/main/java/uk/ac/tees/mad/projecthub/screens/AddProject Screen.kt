package uk.ac.tees.mad.projecthub.screens

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import uk.ac.tees.mad.projecthub.ui.theme.poppins
import uk.ac.tees.mad.projecthub.viewmodels.MainViewModel
import java.io.File

@Composable
fun AddProjectScreen(navController: NavHostController, mainVm: MainViewModel) {
    val context = LocalContext.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    var tempImageUri: Uri? by remember { mutableStateOf(null) }

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            Toast.makeText(context, "Picture taken successfully!", Toast.LENGTH_SHORT).show()
            imageUri.value = tempImageUri
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
    val selectImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri.value = uri
    }


    Scaffold(
        topBar = {
            TopAppBar(title = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = "Add Project",
                        fontFamily = poppins,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }, modifier = Modifier.height(80.dp))
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            AddProjectScreenOptions(
                onProjectSubmit = { projectName, projectDescription, requiredSkills, deadline, budget ->
                    mainVm.addProject(
                        projectName,
                        projectDescription,
                        requiredSkills,
                        deadline,
                        budget,
                        imageUri.value,
                        context
                    )
                },
                openCamera = {
                    permissionLauncher.launch(android.Manifest.permission.CAMERA)
                },
                selectFile = {
                    selectImageLauncher.launch("image/*")
                },
                selectedImageUri = imageUri.value,
                context = context
            )
        }
    }
}

@Composable
fun AddProjectScreenOptions(
    onProjectSubmit: (String, String, String, String, String) -> Unit,
    openCamera: () -> Unit,
    selectFile: () -> Unit,
    selectedImageUri: Uri?,
    context: Context
) {
    var projectName by remember { mutableStateOf("") }
    var projectDescription by remember { mutableStateOf("") }
    var requiredSkills by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }
    var budget by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Add New Project",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        selectedImageUri?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = "Selected Project Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, Color.Gray)
            )
        }
        OutlinedTextField(
            value = projectName,
            onValueChange = { projectName = it },
            label = { Text("Project Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = projectDescription,
            onValueChange = { projectDescription = it },
            label = { Text("Project Description") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 4
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = requiredSkills,
            onValueChange = { requiredSkills = it },
            label = { Text("Required Skills") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = deadline,
            onValueChange = { deadline = it },
            label = { Text("Deadline (e.g., 2024-12-31)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = budget,
            onValueChange = { budget = it },
            label = { Text("Budget (e.g., $5000)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { openCamera() }) {
                Text("Click Project Image")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = { selectFile() }) {
                Text("Choose Project Image")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (selectedImageUri != null) {
                    onProjectSubmit(
                        projectName,
                        projectDescription,
                        requiredSkills,
                        deadline,
                        budget
                    )
                }else{
                    Toast.makeText(context, "Please select an image", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit Project")
        }
    }
}

fun getImageUri(context: Context): Uri {
    val imageFile = File(context.filesDir, "project_image_${System.currentTimeMillis()}.jpg")
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        imageFile
    )
}