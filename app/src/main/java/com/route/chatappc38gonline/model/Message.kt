package com.route.chatappc38gonline.model

data class Message(
    var id: String? = null,
    val content: String? = null,
    val dateTime: Long? = null,
    val senderName: String? = null,
    val senderId: String? = null,

    ) {
    companion object {
        const val COLLECTION_NAME = "Messages"
    }

}