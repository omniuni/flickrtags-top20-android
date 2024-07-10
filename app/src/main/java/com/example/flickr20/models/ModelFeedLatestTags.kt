package com.example.flickr20.models

data class ModelFeedLatestTags(
	val title: String = String(),
	val items: List<ModelFeedItem> = listOf(),
)