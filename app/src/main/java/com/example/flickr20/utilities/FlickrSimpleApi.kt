package com.example.flickr20.utilities

import com.example.flickr20.models.ModelFeedLatestTags
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.net.URL
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FlickrSimpleApi {

	interface IOnApiComplete {
		fun onSuccess(newLatestTags: ModelFeedLatestTags)
		fun onError()
	}

	private val mCaller: IOnApiComplete

	@Suppress("ConvertSecondaryConstructorToPrimary")
	constructor(caller: IOnApiComplete) {
		mCaller = caller
	}

	fun getRequestUrlFromTerms(terms: String): String {
		return "https://api.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1&tags=" + terms
	}

	fun getResults(terms: String) {

		val contents = URL(getRequestUrlFromTerms(terms = terms)).readText()
		val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
		val adapter = moshi.adapter(ModelFeedLatestTags::class.java)
		val modelFeedLatestTags: ModelFeedLatestTags =
			adapter.fromJson(contents) ?: ModelFeedLatestTags()

		// fill in small square (s), large square (q), and medium-large (c) from the medium (m) link
		modelFeedLatestTags.items.forEach {

			it.dateTakenDate =
				LocalDateTime.parse(it.dateTakenString, DateTimeFormatter.ISO_OFFSET_DATE_TIME)

			it.authorClean =
				it.author.substring(
					it.author.lastIndexOf("(\"") + 2,
					it.author.lastIndexOf("\")")
				)


			it.media.s = it.media.m.replace("_m.", "_s.")
			it.media.q = it.media.m.replace("_m.", "_q.")
			it.media.c = it.media.m.replace("_m.", "_c.")
		}

		mCaller.onSuccess(modelFeedLatestTags)

	}

}