package uk.ac.tees.mad.projecthub.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.mad.projecthub.screens.AddProjectScreen
import uk.ac.tees.mad.projecthub.screens.HomeScreen
import uk.ac.tees.mad.projecthub.screens.LoginScreen
import uk.ac.tees.mad.projecthub.screens.SignupScreen
import uk.ac.tees.mad.projecthub.screens.SplashScreen
import uk.ac.tees.mad.projecthub.viewmodels.AuthenticationViewModel
import uk.ac.tees.mad.projecthub.viewmodels.MainViewModel

@Composable
fun ProjectHubNavigation() {
    val navController = rememberNavController()
    val authvm : AuthenticationViewModel = hiltViewModel()
    val mainvm : MainViewModel = hiltViewModel()

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
        composable(NavigationDestination.HomeScreen.name){
            HomeScreen(navController,)
        }
        composable(NavigationDestination.AddProjectScreen.name){
            AddProjectScreen(navController = navController, mainVm = mainvm)
        }
    }
}