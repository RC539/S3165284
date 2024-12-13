package uk.ac.tees.mad.projecthub.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import uk.ac.tees.mad.projecthub.data.model.ProjectModel

@Composable
fun ProjectDetailsScreen(navController: NavHostController, project: ProjectModel) {
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
        ) { AsyncImage(model = project.imageUrl, contentDescription = "Project Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(380.dp)
                .clip(RoundedCornerShape(30.dp)),
            contentScale = ContentScale.FillBounds)
            Column(modifier = Modifier.fillMaxSize().background(brush = Brush.linearGradient(gradientColor))) {

            }
        }
    }
}