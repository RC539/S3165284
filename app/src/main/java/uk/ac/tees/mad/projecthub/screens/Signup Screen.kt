package uk.ac.tees.mad.projecthub.screens

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
import androidx.compose.foundation.layout.width
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
import androidx.compose.material.icons.rounded.Person4
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.ac.tees.mad.projecthub.R
import uk.ac.tees.mad.projecthub.navigation.NavigationDestination
import uk.ac.tees.mad.projecthub.ui.theme.poppins
import uk.ac.tees.mad.projecthub.viewmodels.AuthenticationViewModel

@Composable
fun SignupScreen(navController: NavHostController, authvm: AuthenticationViewModel) {
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val passwordVisible = remember { mutableStateOf(false) }
    val gradientColor = listOf(Color.Blue, Color.White)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(gradientColor))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 70.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(30.dp)
                )
                .clip(
                    RoundedCornerShape(30.dp)
                )
                .background(Color.White)
        ) {
            Image(
                painter = painterResource(id = R.drawable.project), contentDescription = null,
                modifier = Modifier
                    .padding(18.dp)
                    .size(40.dp)
            )
            Text(
                text = "Sign up to start using the App.",
                fontFamily = poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp,
                lineHeight = 34.sp,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Let's sign in to your account and get started.",
                fontFamily = poppins,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Name", fontFamily = poppins, fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.padding(horizontal = 30.dp)
            )
            OutlinedTextField(
                value = name.value,
                onValueChange = { name.value = it },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                shape = RoundedCornerShape(28.dp),
                placeholder = { Text(text = "Your Name", color = Color.LightGray) },
                leadingIcon = { Icon(imageVector = Icons.Rounded.Person4, contentDescription = null) }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Email Address", fontFamily = poppins, fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.padding(horizontal = 30.dp)
            )
            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                shape = RoundedCornerShape(28.dp),
                placeholder = { Text(text = "elementary221b@gmail.com", color = Color.LightGray) },
                leadingIcon = { Icon(imageVector = Icons.Rounded.Email, contentDescription = null) }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Password", fontFamily = poppins, fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 30.dp))
            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                modifier = Modifier
                    .fillMaxWidth().padding(horizontal = 20.dp),
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
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                colors = ButtonDefaults.buttonColors(Color.Blue),
                shape = RoundedCornerShape(28.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Sign Up",
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
            Spacer(modifier = Modifier.height(30.dp))
            Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
                Text(text = "Already have an account?",fontFamily = poppins, fontWeight = FontWeight.SemiBold)
                Text(text = "Log in", color = Color.Blue, fontFamily = poppins, fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable {
                        navController.navigate(NavigationDestination.LoginScreen.name)
                    })
            }
        }
    }
}