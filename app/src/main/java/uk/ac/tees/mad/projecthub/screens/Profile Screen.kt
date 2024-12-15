package uk.ac.tees.mad.projecthub.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import uk.ac.tees.mad.projecthub.viewmodels.AuthenticationViewModel

@Composable
fun ProfileScreen(userVm : AuthenticationViewModel) {
    val gradientColor = listOf(Color.Blue, Color.White)
    val userData =
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
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

            }
        }
    }
}