package com.route.chatappc38gonline.chat

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.route.chatappc38gonline.R
import com.route.chatappc38gonline.chat.ui.theme.ChatAppC38GOnlineTheme
import com.route.chatappc38gonline.model.Constants
import com.route.chatappc38gonline.model.Room
import androidx.lifecycle.viewmodel.compose.viewModel

class ChatActivity : ComponentActivity(), Navigator {
    lateinit var room: Room
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        room = intent.getParcelableExtra(Constants.EXTRA_ROOM, Room::class.java)!!
        setContent {
            ChatAppC38GOnlineTheme {
                ChatScreenContent(room, navigator = this)
            }
        }
    }

    override fun navigateUp() {
        finish()
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreenContent(
    room: Room,
    viewModel: ChatViewModel = viewModel(),
    navigator: Navigator
) {
    viewModel.navigator = navigator
    viewModel.room = room
    Scaffold(contentColor = colorResource(id = R.color.white),
        topBar = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = {
                    viewModel.navigateUp()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "Icon back"
                    )
                }
                Text(
                    text = room.name ?: "",
                    style = TextStyle(fontSize = 22.sp)
                )// title of the room
                Spacer(modifier = Modifier.width(30.dp))
            }
        }

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painterResource(id = R.drawable.background),
                    contentScale = ContentScale.FillBounds
                )
        ) {
            ChatLazyColumn()
        }
    }
}

@Composable
fun ChatLazyColumn(viewModel: ChatViewModel = viewModel()) {
    LazyColumn() {
        items(viewModel.messagesListState.value.size) {
            val item = viewModel.messagesListState.value.get(it)
//            if (item)

        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview5() {
    ChatAppC38GOnlineTheme {
        ChatScreenContent(Room(name = "Hello World"), navigator = object : Navigator {
            override fun navigateUp() {

            }
        })
    }
}