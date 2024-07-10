package com.example.flickr20.views.container

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.flickr20.ui.theme.Flickr20Theme

@Composable
fun ContainerTheme(content: @Composable (PaddingValues) -> Unit) {
	Flickr20Theme {
		Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
			content(innerPadding)
		}
	}
}

@Preview(showBackground = true)
@Composable
fun PreviewContainerTheme() {
	ContainerTheme {
		Text(
			text = "Preview of Container Theme"
		)
	}
}