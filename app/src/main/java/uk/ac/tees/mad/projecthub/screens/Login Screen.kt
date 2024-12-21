package uk.ac.tees.mad.projecthub.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import uk.ac.tees.mad.projecthub.R
import uk.ac.tees.mad.projecthub.navigation.NavigationDestination
import uk.ac.tees.mad.projecthub.ui.theme.poppins
import uk.ac.tees.mad.projecthub.viewmodels.AuthenticationViewModel
import uk.ac.tees.mad.projecthub.viewmodels.MainViewModel


@Composable
fun LoginScreen(navController: NavController, authvm: AuthenticationViewModel) {
    val context = LocalContext.current
    val loading = authvm.loading
    val loggedIn = authvm.isUserSignedIn
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val passwordVisible = remember { mutableStateOf(false) }
    LaunchedEffect(loggedIn.value) {
        if (loggedIn.value) {
            navController.navigate(NavigationDestination.HomeScreen.name) {
                popUpTo(0)
            }
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        val gradientColor = listOf(Color.Cyan, Color.Blue)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = Brush.sweepGradient(gradientColor))
            ) {}
            Column(modifier = Modifier.statusBarsPadding()) {
                Row(modifier = Modifier.padding(top = 40.dp, start = 20.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.project),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(
                                CircleShape
                            )
                            .background(Color.White)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Project Hub",
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 22.sp,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
                Spacer(modifier = Modifier.height(25.dp))
                Text(
                    text = "Begin your journey with Project Hub today.",
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 28.sp,
                    color = Color.White,
                    lineHeight = 34.sp,
                    modifier = Modifier.padding(horizontal = 22.dp)
                )
            }
        }
        Column(modifier = Modifier.padding(18.dp)) {
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Sign In To Your Account.",
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 23.sp,
                color = MaterialTheme.colors.onBackground

            )
            Text(text = "Let's sign in to your account and get started.")
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Email Address", fontFamily = poppins, fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.onBackground
            )
            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                placeholder = { Text(text = "elementary221b@gmail.com", color = Color.LightGray) },
                leadingIcon = { Icon(imageVector = Icons.Rounded.Email, contentDescription = null) }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Password", fontFamily = poppins, fontWeight = FontWeight.SemiBold)
            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                placeholder = {
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "**********", color = Color.LightGray)
                },
                leadingIcon = { Icon(imageVector = Icons.Rounded.Lock, contentDescription = null) },
                visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon =
                        if (passwordVisible.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = {
                        passwordVisible.value = !passwordVisible.value
                    }) {
                        Icon(imageVector = icon, contentDescription = null)
                    }
                }
            )
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = { authvm.logIn(email.value, password.value, onResult = { isSuccess, message ->
                }) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.Blue),
                shape = RoundedCornerShape(28.dp)
            ) {
                if (loading.value) {
                    CircularProgressIndicator()
                } else {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Sign in",
                            fontFamily = poppins,
                            color = Color.White,
                            fontSize = 22.sp,
                        )
                        Icon(
                            imageVector = Icons.Filled.Login,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
                Text(text = "Don't have an account?",fontFamily = poppins, fontWeight = FontWeight.SemiBold)
                Text(text = "Sign Up", color = Color.Blue, fontFamily = poppins, fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable {
                        navController.navigate(NavigationDestination.SignupScreen.name)
                    })
            }
        }
    }
}