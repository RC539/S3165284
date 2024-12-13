package uk.ac.tees.mad.projecthub.navigation
import android.net.Uri
import com.google.gson.Gson
import uk.ac.tees.mad.projecthub.data.model.ProjectModel

enum class NavigationDestination {
    SplashScreen,
    LoginScreen,
    SignupScreen,
    HomeScreen,
    AddProjectScreen,
    ProjectDetailScreen
}


fun buildProjectDetailRoute(projectModel: ProjectModel): String {
    val projectJson = Uri.encode(Gson().toJson(projectModel))
    return "${NavigationDestination.ProjectDetailScreen.name}/$projectJson"
}
