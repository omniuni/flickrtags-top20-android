package com.example.flickr20.views.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.flickr20.activities.interfaces.INavigationFunctions
import com.example.flickr20.models.ModelFeedItem
import java.time.format.DateTimeFormatter

@Composable
fun HomeScreenListItem(navigationFunctions: INavigationFunctions, modelFeedItem: ModelFeedItem) {
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.padding(6.dp)
			.clickable {
				navigationFunctions.navigateToDetails(modelFeedItem = modelFeedItem)
			}
	) {
		Row(
			modifier = Modifier.padding(6.dp),
			verticalAlignment = Alignment.CenterVertically
		) {
			Box(
				modifier = Modifier.clip(shape = RoundedCornerShape(5.dp)),
			) {
				AsyncImage(
					modifier = Modifier
						.size(75.dp)
						.padding(end = 6.dp)
						.clip(shape = RoundedCornerShape(4.dp)),
					model = modelFeedItem.media.s,
					contentDescription = String(),
					contentScale = ContentScale.Crop
				)
			}
			Column {
				Text(
					maxLines = 1,
					text = modelFeedItem.title
				)
				Text(
					maxLines = 1,
					text = "by ${modelFeedItem.authorClean}"
				)
				Text(
					maxLines = 1,
					text = modelFeedItem.dateTakenDate.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"))
				)
			}
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
fun HomeScreenListItemPreview() {
	HomeScreenListItem(
		navigationFunctions = emptyNavigationFunctions,
		modelFeedItem = modelFeedItem
	)
}