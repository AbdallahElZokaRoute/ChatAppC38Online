package com.route.chatappc38gonline.model

data class Room(
    var roomId: String?,
    val name: String?,
    val description: String?,
    val categoryID: String
) {

    companion object {
        const val COLLECTION_NAME = "Room"
    }
}