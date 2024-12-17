package uk.ac.tees.mad.projecthub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import uk.ac.tees.mad.projecthub.navigation.ProjectHubNavigation
import uk.ac.tees.mad.projecthub.screens.LoginScreen
import uk.ac.tees.mad.projecthub.screens.SignupScreen
import uk.ac.tees.mad.projecthub.screens.SplashScreen
import uk.ac.tees.mad.projecthub.ui.theme.ProjectHubTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val systemDarkMode = isSystemInDarkTheme()
            var isDarkMode by remember { mutableStateOf(systemDarkMode) }
            ProjectHubTheme(darkTheme = isDarkMode){
                ProjectHubNavigation(onToggle = { isDarkMode = !isDarkMode })
            }
        }
    }
}
