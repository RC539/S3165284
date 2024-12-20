package uk.ac.tees.mad.projecthub.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import uk.ac.tees.mad.projecthub.data.model.ProjectModel
import uk.ac.tees.mad.projecthub.screens.AddProjectScreen
import uk.ac.tees.mad.projecthub.screens.HomeScreen
import uk.ac.tees.mad.projecthub.screens.LoginScreen
import uk.ac.tees.mad.projecthub.screens.ProfileScreen
import uk.ac.tees.mad.projecthub.screens.ProjectDetailsScreen
import uk.ac.tees.mad.projecthub.screens.SignupScreen
import uk.ac.tees.mad.projecthub.screens.SplashScreen
import uk.ac.tees.mad.projecthub.viewmodels.AuthenticationViewModel
import uk.ac.tees.mad.projecthub.viewmodels.MainViewModel

@Composable
fun ProjectHubNavigation(onToggle:()-> Unit) {
    val navController = rememberNavController()
    val authvm: AuthenticationViewModel = hiltViewModel()
    val mainvm: MainViewModel = hiltViewModel()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){
        NavHost(
            navController = navController,
            startDestination = NavigationDestination.SplashScreen.name
        ) {
            composable(NavigationDestination.SplashScreen.name) {
                SplashScreen(navController, authvm)
            }
            composable(NavigationDestination.LoginScreen.name) {
                LoginScreen(navController, authvm)
            }
            composable(NavigationDestination.SignupScreen.name) {
                SignupScreen(navController, authvm)
            }
            composable(NavigationDestination.HomeScreen.name) {
                HomeScreen(navController, mainvm)
            }
            composable(NavigationDestination.AddProjectScreen.name) {
                AddProjectScreen(navController = navController, mainVm = mainvm)
            }
            composable(
                route = "${NavigationDestination.ProjectDetailScreen.name}/{projectModel}",
                arguments = listOf(navArgument("projectModel") { type = NavType.StringType })
            ) { backStackEntry ->
                val projectJson = backStackEntry.arguments?.getString("projectModel")
                val projectModel = Gson().fromJson(projectJson, ProjectModel::class.java)
                ProjectDetailsScreen(navController, projectModel, mainvm)
            }
            composable(NavigationDestination.ProfileScreen.name) {
                ProfileScreen(userVm = authvm, onToggle = {onToggle()}, navController)
            }
        }
    }
}