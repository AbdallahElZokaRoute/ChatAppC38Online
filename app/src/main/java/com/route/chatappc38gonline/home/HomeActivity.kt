package com.route.chatappc38gonline.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.route.chatappc38gonline.R
import com.route.chatappc38gonline.home.ui.theme.ChatAppC38GOnlineTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.route.chatappc38gonline.addRoom.AddRoomActivity

class HomeActivity : ComponentActivity(), Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppC38GOnlineTheme {
                // A surface container using the 'background' color from the theme
                HomeContent(navigator = this)
            }
        }
    }

    override fun navigateToAddRoomScreen() {
        val intent = Intent(this@HomeActivity, AddRoomActivity::class.java)
        startActivity(intent)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(viewModel: HomeViewModel = viewModel(), navigator: Navigator) {
    viewModel.navigator = navigator
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Text(
                text = stringResource(id = R.string.home),
                style = TextStyle(
                    color = colorResource(
                        id = R.color.white
                    ),
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth(),
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.navigateToAddRoomScreen()
                },
                contentColor = colorResource(id = R.color.white),
                containerColor = colorResource(
                    id = R.color.blue
                )
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_add), contentDescription = " ")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = R.drawable.background),
                    contentScale = ContentScale.FillBounds // fitXY
                )
        ) {

        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview3() {
    ChatAppC38GOnlineTheme {
        HomeContent(navigator = object : Navigator {
            override fun navigateToAddRoomScreen() {

            }

        })
    }
}