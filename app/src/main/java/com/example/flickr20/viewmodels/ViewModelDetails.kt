package com.example.flickr20.viewmodels

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.flickr20.models.ModelFeedItem
import com.example.flickr20.viewmodels.interfaces.IViewModelDetails

class ViewModelDetails(application: Application) : AndroidViewModel(application),
	IViewModelDetails {

	override val activeFeedItem: MutableState<ModelFeedItem> = mutableStateOf(ModelFeedItem())

	fun setActiveItem(modelFeedItem: ModelFeedItem) {
		activeFeedItem.value = modelFeedItem
	}

}