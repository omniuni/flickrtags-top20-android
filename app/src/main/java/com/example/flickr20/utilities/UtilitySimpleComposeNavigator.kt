package com.example.flickr20.utilities

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable

class UtilitySimpleComposeNavigator {

	companion object {
		private var mActivity: ComponentActivity? = null
		private val mComposableStack = arrayListOf<@Composable () -> Unit>()
	}

	constructor()
	constructor(activity: ComponentActivity) {
		mActivity = activity
	}

	fun initialize(content: @Composable () -> Unit) {
		if (mComposableStack.isEmpty()) {
			push(content)
		} else {
			updateView()
		}
	}

	fun push(content: @Composable () -> Unit) {
		mActivity?.also {
			mComposableStack.add(content)
			updateView()
		}
	}

	fun pop() {
		if (mComposableStack.size > 1) {
			mComposableStack.removeLast()
			updateView()
		} else {
			mComposableStack.clear()
			mActivity?.also {
				it.finish()
			}
		}
	}

	private fun updateView() {
		mActivity?.also {
			it.setContent(content = mComposableStack.last())
		}
	}

}