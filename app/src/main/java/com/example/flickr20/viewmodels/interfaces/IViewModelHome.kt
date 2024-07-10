package com.example.flickr20.viewmodels.interfaces

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.MutableState
import com.example.flickr20.models.ModelFeedLatestTags

interface IViewModelHome {

	val searchTerm: MutableState<String>
	val latestTags: MutableState<ModelFeedLatestTags>
	val searching: MutableState<Boolean>
	val showDetails: MutableState<Boolean>
	var lazyListState: LazyListState

	fun updateSearchTerm(newSearchTerm: String)

}