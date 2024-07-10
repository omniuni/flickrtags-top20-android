package com.example.flickr20.views.screens.detail

import android.content.res.ColorStateList
import android.content.res.Configuration
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImage
import com.example.flickr20.models.ModelFeedItem
import com.example.flickr20.utilities.UtilitySimpleComposeNavigator
import com.example.flickr20.viewmodels.interfaces.IViewModelDetails
import com.example.flickr20.views.container.ContainerTheme
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
	viewModel: IViewModelDetails,
) {
	ContainerTheme {
		Scaffold(
			topBar = {
				CenterAlignedTopAppBar(
					colors = TopAppBarDefaults.mediumTopAppBarColors(
						containerColor = MaterialTheme.colorScheme.primaryContainer
					),
					title = {
						Text(
							text = viewModel.activeFeedItem.value.title,
							maxLines = 2
						)
					},
					navigationIcon = {
						Icon(
							Icons.Default.Close,
							contentDescription = "Close",
							modifier = Modifier.clickable {
								UtilitySimpleComposeNavigator().pop()
							}
						)
					}
				)
			}
		) { innerPadding ->
			Box(
				modifier = Modifier
					.fillMaxSize()
					.padding(innerPadding)
			) {
				Column(
					modifier = Modifier
						.fillMaxWidth()
						.padding(12.dp)
				) {
					Box(
						modifier = Modifier.clip(shape = RoundedCornerShape(5.dp)),
					) {
						AsyncImage(
							modifier = Modifier
								.fillMaxWidth()
								.fillMaxHeight(0.5f)
								.padding(end = 6.dp)
								.clip(shape = RoundedCornerShape(4.dp)),
							model = viewModel.activeFeedItem.value.media.c,
							contentDescription = String(),
							contentScale = ContentScale.Fit
						)
					}
					Text(
						modifier = Modifier
							.fillMaxWidth()
							.padding(12.dp),
						style = MaterialTheme.typography.headlineSmall,
						text = "by ${viewModel.activeFeedItem.value.authorClean}",
						textAlign = TextAlign.Center
					)
					val formattedDate = viewModel.activeFeedItem.value.dateTakenDate.format(
						DateTimeFormatter.ofPattern("MMM dd, yyyy")
					)
					Text(
						modifier = Modifier
							.fillMaxWidth()
							.padding(bottom = 12.dp),
						text = "on $formattedDate",
						textAlign = TextAlign.Center
					)
					val textColor: Int = MaterialTheme.colorScheme.onBackground.toArgb()
					val textColorLinks: Int = MaterialTheme.colorScheme.primary.toArgb()
					AndroidView(
						modifier = Modifier.fillMaxWidth(),
						factory = { context ->
							TextView(context)
						},
						update = {
							it.apply {
								text = HtmlCompat.fromHtml(
									viewModel.activeFeedItem.value.description.replace(
										Regex("<img.+?>"),
										String()
									),
									HtmlCompat.FROM_HTML_MODE_COMPACT
								)
								textSize = 18f
								setTextColor(ColorStateList.valueOf(textColor))
								setLinkTextColor(ColorStateList.valueOf(textColorLinks))
								linksClickable = true
								movementMethod = LinkMovementMethod.getInstance()
							}
						}
					)
				}

			}
		}

	}
}

private var detailScreenFeeditem = ModelFeedItem(
	title = "Title One",
	author = "Author One",
	authorClean = "Author One Clean",
	dateTakenString = "Date String One",
	description = "<b>Bold</b> Description <a href>Link</a>"
)

private var detailScreenPreviewViewModel = object : IViewModelDetails {
	override val activeFeedItem: MutableState<ModelFeedItem> = mutableStateOf(detailScreenFeeditem)
}

@Preview
@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewDetailScreen() {
	DetailScreen(
		viewModel = detailScreenPreviewViewModel,
	)
}