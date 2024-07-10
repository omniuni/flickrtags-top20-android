package com.example.flickr20.models

import com.squareup.moshi.Json
import java.time.LocalDateTime

data class ModelFeedItem(
	val title: String = String(),
	val description: String = String(),
	val link: String = String(),
	@Json(name = "date_taken")
	val dateTakenString: String = String(),
	val author: String = String(),
	var authorClean: String = String(),
	val media: ModelFeedItemMedia = ModelFeedItemMedia(),
	@Transient
	var dateTakenDate: LocalDateTime = LocalDateTime.now(),
)
