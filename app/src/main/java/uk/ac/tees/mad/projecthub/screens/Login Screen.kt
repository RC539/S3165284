package uk.ac.tees.mad.projecthub.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        val gradientColor = listOf(Color.Cyan, Color.Blue)
        Box {
            Column(modifier = Modifier.fillMaxWidth().size(220.dp).background(brush = Brush.sweepGradient(gradientColor))) {

            }
        }

    }
}