package uk.ac.tees.mad.projecthub.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import uk.ac.tees.mad.projecthub.R
import uk.ac.tees.mad.projecthub.navigation.NavigationDestination
import uk.ac.tees.mad.projecthub.viewmodels.AuthenticationViewModel

@Composable
fun SplashScreen(navController: NavController, authvm: AuthenticationViewModel) {
    val isSigned = authvm.isUserSignedIn
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    val alpha = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = androidx.compose.animation.core.tween(
                durationMillis = 500,
                easing = androidx.compose.animation.core.EaseInBounce
            )
        )
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = androidx.compose.animation.core.tween(
                durationMillis = 500,
                easing = androidx.compose.animation.core.EaseInOutBounce
            )
        )
        delay(1500L)
        scale.animateTo(
            targetValue = 0f,
            animationSpec = androidx.compose.animation.core.tween(
                durationMillis = 500,
                easing = androidx.compose.animation.core.EaseInBounce
            )
        )
        alpha.animateTo(
            targetValue = 0f,
            animationSpec = androidx.compose.animation.core.tween(
                durationMillis = 500,
                easing = androidx.compose.animation.core.EaseInOutBounce
            )
        )
        if (isSigned.value) {
            navController.navigate(NavigationDestination.HomeScreen.name){
                popUpTo(0)
            }
        }else {
            navController.navigate(NavigationDestination.LoginScreen.name)
        }
    }
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier
            .scale(scale.value)
            .alpha(alpha.value)
            .clip(CircleShape)
            .background(Color.LightGray)
            .border(2.dp, Color.Black, shape = CircleShape)) {
            Image(painter = painterResource(id = R.drawable.project), contentDescription =null)
        }
        Text(text = "Project Hub", fontSize = 30.sp)
    }
}