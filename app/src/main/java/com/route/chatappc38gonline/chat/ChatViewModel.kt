package com.route.chatappc38gonline.chat

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentChange
import com.route.chatappc38gonline.database.getMessagesFromFirestoreDB
import com.route.chatappc38gonline.model.Message
import com.route.chatappc38gonline.model.Room

class ChatViewModel : ViewModel() {
    var navigator: Navigator? = null
    var room: Room? = null
    val messagesListState = mutableStateOf<List<Message>>(listOf())
    fun navigateUp() {
        navigator?.navigateUp()
    }

    fun getMessagesFromFirestore() {
        getMessagesFromFirestoreDB(roomId = room?.roomId!!, listener = { snapshots, e ->
            if (e != null) {
                Log.e("Tag", "${e.message}")
                return@getMessagesFromFirestoreDB
            }
            val mutableList = mutableListOf<Message>()
            for (dc in snapshots!!.documentChanges) {
                when (dc.type) {
                    DocumentChange.Type.ADDED -> {
                        mutableList.add(dc.document.toObject(Message::class.java))
                    }
//                    DocumentChange.Type.MODIFIED -> Log.d("tag", "Modified city: ${dc.document.data}")
//                    DocumentChange.Type.REMOVED -> Log.d("tag", "Removed city: ${dc.document.data}")
                    else -> {}
                }
            }
            messagesListState.value = mutableList
        })
    }


}
