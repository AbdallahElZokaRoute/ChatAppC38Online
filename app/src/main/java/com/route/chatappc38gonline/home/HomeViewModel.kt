package com.route.chatappc38gonline.home

import androidx.lifecycle.ViewModel
import com.route.chatappc38gonline.model.Category

class HomeViewModel : ViewModel() {

    var navigator: Navigator? = null

    fun navigateToAddRoomScreen() {
        navigator?.navigateToAddRoomScreen()
    }
}
