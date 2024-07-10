package com.example.flickr20.views.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.flickr20.activities.interfaces.INavigationFunctions
import com.example.flickr20.models.ModelFeedItem
import java.time.format.DateTimeFormatter

@Composable
fun HomeScreenGridItem(navigationFunctions: INavigationFunctions, modelFeedItem: ModelFeedItem) {
	Card(
		modifier = Modifier
			.fillMaxSize()
			.padding(6.dp)
			.clickable {
				navigationFunctions.navigateToDetails(modelFeedItem = modelFeedItem)
			}
	) {
		Box(
			modifier = Modifier.clip(shape = RoundedCornerShape(5.dp)),
			contentAlignment = Alignment.BottomStart
		) {
			AsyncImage(
				modifier = Modifier
					.fillMaxSize()
					.clip(shape = RoundedCornerShape(4.dp)),
				model = modelFeedItem.media.q,
				contentDescription = String(),
				contentScale = ContentScale.Crop
			)
			Text(
				modifier = Modifier.padding(start = 6.dp),
				style = TextStyle(
					shadow = Shadow(
						color = MaterialTheme.colorScheme.inverseOnSurface,
						blurRadius = 2f,
						offset = Offset(2f, 2f)
					)
				),
				maxLines = 1,
				text = modelFeedItem.dateTakenDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
			)
		}
	}

}

private var modelFeedItem = ModelFeedItem(
	title = "Title One",
	author = "Author One",
	dateTakenString = "Date String One"
)

private var emptyNavigationFunctions = object : INavigationFunctions {
	override fun navigateToDetails(modelFeedItem: ModelFeedItem) {}
	override fun navigatePop() {}
}

@Preview
@Composable
fun HomeScreenGridItemPreview() {
	HomeScreenGridItem(
		navigationFunctions = emptyNavigationFunctions,
		modelFeedItem = modelFeedItem
	)
}