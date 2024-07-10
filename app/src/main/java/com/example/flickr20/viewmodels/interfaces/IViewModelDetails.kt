package com.example.flickr20.viewmodels.interfaces

import androidx.compose.runtime.MutableState
import com.example.flickr20.models.ModelFeedItem

interface IViewModelDetails {
	val activeFeedItem: MutableState<ModelFeedItem>
}