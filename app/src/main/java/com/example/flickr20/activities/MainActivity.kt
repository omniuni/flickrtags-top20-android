package com.example.flickr20.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.flickr20.activities.interfaces.INavigationFunctions
import com.example.flickr20.models.ModelFeedItem
import com.example.flickr20.utilities.UtilitySimpleComposeNavigator
import com.example.flickr20.viewmodels.ViewModelDetails
import com.example.flickr20.viewmodels.ViewModelHome
import com.example.flickr20.views.screens.detail.DetailScreen
import com.example.flickr20.views.screens.home.HomeScreen

class MainActivity : ComponentActivity(), INavigationFunctions {


	private val mNavigator = UtilitySimpleComposeNavigator(this)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		val viewModelHome: ViewModelHome by viewModels<ViewModelHome>()
		mNavigator.initialize {
			HomeScreen(
				viewModel = viewModelHome,
				navigationFunctions = this,
			)
		}
		onBackPressedDispatcher.addCallback(onBackPressedCallback)
	}

	override fun navigateToDetails(modelFeedItem: ModelFeedItem) {
		val viewModelDetails: ViewModelDetails by viewModels<ViewModelDetails>()
		viewModelDetails.setActiveItem(modelFeedItem)
		mNavigator.push {
			DetailScreen(
				viewModel = viewModelDetails
			)
		}
	}

	override fun navigatePop() {
		mNavigator.pop()
	}

	private val onBackPressedCallback = object : OnBackPressedCallback(true) {
		override fun handleOnBackPressed() {
			navigatePop()
		}
	}


}

