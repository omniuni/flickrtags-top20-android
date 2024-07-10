package com.example.flickr20.activities.interfaces

import com.example.flickr20.models.ModelFeedItem

interface INavigationFunctions {
	fun navigateToDetails(modelFeedItem: ModelFeedItem)
	fun navigatePop()
}