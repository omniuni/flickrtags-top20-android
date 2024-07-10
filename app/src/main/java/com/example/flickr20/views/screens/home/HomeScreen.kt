package com.example.flickr20.views.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flickr20.activities.interfaces.INavigationFunctions
import com.example.flickr20.models.ModelFeedItem
import com.example.flickr20.models.ModelFeedLatestTags
import com.example.flickr20.viewmodels.interfaces.IViewModelHome
import com.example.flickr20.views.container.ContainerTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
	viewModel: IViewModelHome,
	navigationFunctions: INavigationFunctions,
) {
	val refreshing by remember { mutableStateOf(false) }
	val pullRefreshState = rememberPullRefreshState(
		refreshing = refreshing,
		{ viewModel.updateSearchTerm(viewModel.searchTerm.value) }
	)
	ContainerTheme { paddingValues ->
		Column(
			modifier = Modifier.padding(paddingValues = paddingValues)
		) {
			Text(
				modifier = Modifier.padding(12.dp),
				text = "Enter tags, separated by commas, to search."
			)
			Card(
				modifier = Modifier
					.fillMaxWidth()
					.padding(horizontal = 12.dp),

				) {
				Row(
					modifier = Modifier
						.fillMaxWidth()
						.padding(horizontal = 12.dp, vertical = 6.dp),
					horizontalArrangement = Arrangement.spacedBy(12.dp),
					verticalAlignment = Alignment.CenterVertically,
				) {
					TextField(
						modifier = Modifier.weight(1f),
						value = viewModel.searchTerm.value,
						onValueChange = { viewModel.updateSearchTerm(it) },
						placeholder = {
							Text(
								style = MaterialTheme.typography.bodySmall,
								text = "Enter tags here."
							)
						}
					)
					Box(
						modifier = Modifier.size(40.dp),
						contentAlignment = Alignment.Center
					) {
						if (viewModel.showDetails.value) {
							Icon(
								modifier = Modifier
									.size(32.dp)
									.clickable {
										viewModel.showDetails.value = !viewModel.showDetails.value
									},
								imageVector = Icons.Default.Menu,
								contentDescription = "Showing Details"
							)
						} else {
							Icon(
								modifier = Modifier
									.size(32.dp)
									.clickable {
										viewModel.showDetails.value = !viewModel.showDetails.value
									},
								imageVector = Icons.Default.MoreVert,
								contentDescription = "Showing Details"
							)
						}
						if (viewModel.searching.value) {
							CircularProgressIndicator()
						}
					}
				}
			}
			Text(
				modifier = Modifier
					.fillMaxWidth()
					.padding(12.dp),
				style = MaterialTheme.typography.headlineMedium,
				text = viewModel.latestTags.value.title,
				maxLines = 2,
				textAlign = TextAlign.Center
			)
			val alpha = if (viewModel.searching.value) {
				0.5f
			} else {
				1f
			}
			Box(
				modifier = Modifier.pullRefresh(pullRefreshState)
			) {
				if (viewModel.showDetails.value) {
					LazyColumn(
						modifier = Modifier.alpha(alpha),
						state = viewModel.lazyListState
					) {
						items(viewModel.latestTags.value.items) {
							HomeScreenListItem(navigationFunctions, it)
						}
					}
				} else {
					LazyVerticalGrid(columns = GridCells.Fixed(3)) {
						items(viewModel.latestTags.value.items) {
							HomeScreenGridItem(navigationFunctions, it)
						}
					}
				}
				PullRefreshIndicator(
					modifier = Modifier.align(Alignment.TopCenter),
					refreshing = refreshing,
					state = pullRefreshState
				)
			}

		}
	}
}

private var homeScreenPreviewViewModel = object : IViewModelHome {
	override val searchTerm: MutableState<String> = mutableStateOf("Preview")
	override val latestTags: MutableState<ModelFeedLatestTags> = mutableStateOf(
		ModelFeedLatestTags(
			title = "Uploads from ...",
			items = listOf(
				ModelFeedItem(
					title = "Title One",
					author = "Author One",
					dateTakenString = "Date String One"
				),
				ModelFeedItem(
					title = "Title Two",
					author = "Author Two",
					dateTakenString = "Date String Two"
				)
			)
		)
	)
	override val searching: MutableState<Boolean> = mutableStateOf(false)
	override val showDetails: MutableState<Boolean> = mutableStateOf(false)
	override var lazyListState: LazyListState = LazyListState()
	override fun updateSearchTerm(newSearchTerm: String) {}
}

private var emptyNavigationFunctions = object : INavigationFunctions {
	override fun navigateToDetails(modelFeedItem: ModelFeedItem) {}
	override fun navigatePop() {}
}

@Preview
@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewHomeScreen() {
	HomeScreen(
		viewModel = homeScreenPreviewViewModel,
		navigationFunctions = emptyNavigationFunctions,
	)
}
