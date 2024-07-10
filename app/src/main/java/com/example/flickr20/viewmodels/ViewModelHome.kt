package com.example.flickr20.viewmodels

import android.app.Application
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.flickr20.models.ModelFeedLatestTags
import com.example.flickr20.utilities.FlickrSimpleApi
import com.example.flickr20.viewmodels.interfaces.IViewModelHome
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ViewModelHome(application: Application) : AndroidViewModel(application), IViewModelHome,
	FlickrSimpleApi.IOnApiComplete {

	private var apiJobScope = viewModelScope.launch(Dispatchers.IO) { executeSearch() }

	override val searchTerm = mutableStateOf(String())
	override val latestTags: MutableState<ModelFeedLatestTags> =
		mutableStateOf(ModelFeedLatestTags())
	override val searching: MutableState<Boolean> = mutableStateOf(false)
	override val showDetails: MutableState<Boolean> = mutableStateOf(false)

	override var lazyListState: LazyListState by mutableStateOf(LazyListState(0, 0))

	override fun updateSearchTerm(newSearchTerm: String) {
		searchTerm.value = newSearchTerm
		apiJobScope.cancel()
		searching.value = false
		apiJobScope = viewModelScope.launch(Dispatchers.IO) { executeSearch() }
	}

	private suspend fun executeSearch() {
		delay(1000)
		searching.value = true
		FlickrSimpleApi(this@ViewModelHome).getResults(searchTerm.value)
	}

	override fun onSuccess(newLatestTags: ModelFeedLatestTags) {
		searching.value = false
		latestTags.value = newLatestTags
	}

	override fun onError() {
		searching.value = false
		latestTags.value = ModelFeedLatestTags()
	}

}