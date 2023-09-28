package com.route.chatappc38gonline.home

import com.route.chatappc38gonline.model.Room

interface Navigator {
    fun navigateToAddRoomScreen()
    fun navigateToChatScreen(room: Room)
}