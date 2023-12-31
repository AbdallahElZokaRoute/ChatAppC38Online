package com.route.chatappc38gonline.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import com.route.chatappc38gonline.R
import com.route.chatappc38gonline.home.HomeActivity
import com.route.chatappc38gonline.login.LoginActivity
import com.route.chatappc38gonline.register.RegisterActivity
import com.route.chatappc38gonline.ui.theme.ChatAppC38GOnlineTheme
import androidx.lifecycle.viewmodel.compose.viewModel

class SplashActivity : ComponentActivity(), Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppC38GOnlineTheme {
                // A surface container using the 'background' color from the theme
                SplashContent(navigator = this@SplashActivity)
            }
        }
    }

    override fun navigateToHomeScreen() {
        val intent = Intent(this@SplashActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun navigateToLoginScreen() {
        val intent = Intent(this@SplashActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}

@Composable
fun SplashContent(
    viewModel: SplashViewModel = viewModel(),
    navigator: Navigator
) {
    viewModel.navigator = navigator
    viewModel.navigate()
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (logo, signature) = createRefs()
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            modifier = Modifier
                .constrainAs(logo) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxSize(0.35F)
        )
        Image(
            painter = painterResource(id = R.drawable.signature),
            contentDescription = "App Signature", modifier = Modifier
                .constrainAs(signature) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(0.4F)
                .fillMaxHeight(0.2F)
        )


    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ChatAppC38GOnlineTheme {
        SplashContent(navigator = object : Navigator {
            override fun navigateToHomeScreen() {

            }

            override fun navigateToLoginScreen() {

            }

        })
    }
}