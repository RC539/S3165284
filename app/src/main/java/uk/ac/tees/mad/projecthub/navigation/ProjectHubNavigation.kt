package uk.ac.tees.mad.projecthub.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.mad.projecthub.screens.LoginScreen
import uk.ac.tees.mad.projecthub.screens.SignupScreen
import uk.ac.tees.mad.projecthub.screens.SplashScreen
import uk.ac.tees.mad.projecthub.viewmodels.AuthenticationViewModel
import uk.ac.tees.mad.projecthub.viewmodels.MainViewModel

@Composable
fun ProjectHubNavigation() {
    val navController = rememberNavController()
    val authvm : AuthenticationViewModel = viewModel()
    val mainvm : MainViewModel = viewModel()

    NavHost(navController = navController,startDestination = NavigationDestination.SplashScreen.name){
        composable(NavigationDestination.SplashScreen.name){
            SplashScreen(navController, authvm)
        }
        composable(NavigationDestination.LoginScreen.name){
            LoginScreen(navController, authvm)
        }
        composable(NavigationDestination.SignupScreen.name){
            SignupScreen(navController, authvm)
        }
    }
}