package com.route.chatappc38gonline.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.route.chatappc38gonline.database.getRoomsFromFirestoreDB
import com.route.chatappc38gonline.model.Category
import com.route.chatappc38gonline.model.Room

class HomeViewModel : ViewModel() {

    var navigator: Navigator? = null

    val roomsListState = mutableStateOf<List<Room>>(listOf())
    fun navigateToAddRoomScreen() {
        navigator?.navigateToAddRoomScreen()
    }

    fun navigateToChatScreen(room: Room) {
        navigator?.navigateToChatScreen(room)
    }

    fun getRoomsFromFirestore() {
        getRoomsFromFirestoreDB(onSuccessListener = {
            val list = mutableListOf<Room>()
            it.documents.forEach {
                list.add(it.toObject(Room::class.java) ?: return@forEach)
            }
            roomsListState.value = list
        }, onFailureListener = {

        })
    }

}
