package com.route.chatappc38gonline.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.route.chatappc38gonline.R
import com.route.chatappc38gonline.home.HomeActivity
import com.route.chatappc38gonline.register.RegisterActivity
import com.route.chatappc38gonline.register.ui.theme.ChatAppC38GOnlineTheme

class LoginActivity : ComponentActivity(), Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppC38GOnlineTheme {
                // A surface container using the 'background' color from the theme
                LoginContent(navigator = this)
            }
        }
    }

    override fun openHomeActivity() {
        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun openRegisterActivity() {
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginContent(viewModel: LoginViewModel = viewModel(), navigator: Navigator) {
    viewModel.navigator = navigator
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {

            Text(
                text = "Login", modifier = Modifier
                    .padding(vertical = 8.0.dp)
                    .fillMaxWidth(),
                style = TextStyle(
                    color = colorResource(id = R.color.white),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            )
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = R.drawable.background),
                    contentScale = ContentScale.FillBounds // scaleType = fitXY
                )
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.4F))
            ChatAuthTextField(
                state = viewModel.emailState,
                label = "Email",
                errorState = viewModel.emailError
            )
            ChatAuthTextField(
                state = viewModel.passwordState,
                label = "Password",
                errorState = viewModel.passwordError,
                isPassword = true
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            ChatButton(buttonText = "Login") {
                viewModel.sendAuthDataToFirebase()
            }
            TextButton(onClick = {
                viewModel.navigateToRegisterScreen()
            }) {
                Text(
                    text = "or Create my account",
                    style = TextStyle(color = colorResource(id = R.color.colorGrey))
                )
            }
            LoadingDialog()
            ChatAlertDialog()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatAuthTextField(
    state: MutableState<String>,
    label: String,
    errorState: MutableState<String>,
    isPassword: Boolean = false
) {
    TextField(
        value = state.value, onValueChange = { newValue ->
            state.value = newValue
        }, colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
        label = {
            Text(
                text = label,
                style = TextStyle(
                    color = colorResource(id = R.color.grey),
                    fontSize = 14.sp
                )
            )
        },
        isError = errorState.value.isNotEmpty(),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = if (isPassword) KeyboardOptions(keyboardType = KeyboardType.Password) else KeyboardOptions(),
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .fillMaxWidth()

    )
    if (errorState.value.isNotEmpty()) {
        Text(
            text = errorState.value,
            style = TextStyle(color = Color.Red, fontSize = 13.sp),
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 1.dp)
        )
    }

}

@Composable
fun LoadingDialog(viewModel: LoginViewModel = viewModel()) {
    if (viewModel.showLoading.value)
        Dialog(onDismissRequest = { }) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .background(
                        color = colorResource(id = R.color.white),
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(35.dp)
                        .height(35.dp),
                    color = colorResource(id = R.color.blue)
                )
            }
        }
}


@Composable
fun ChatAlertDialog(viewModel: LoginViewModel = viewModel()) {
    if (viewModel.message.value.isNotEmpty())
        AlertDialog(

            onDismissRequest = {
                viewModel.message.value = ""
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.message.value = ""
                }) { Text(text = "OK") }
            },
            text = { Text(text = viewModel.message.value) },
            modifier = Modifier.fillMaxWidth()
        )
}

@Composable
@Preview(name = "Preview")
fun ChatAlertDialogPreview() {
    ChatAlertDialog()
}

@Composable
fun ChatButton(buttonText: String, onButtonClick: () -> Unit) {
    Button(
        onClick = {
            onButtonClick()
        }, colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(
                id = R.color.blue

            ),
            contentColor = colorResource(id = R.color.white)
        ),
        shape = RoundedCornerShape(corner = CornerSize(6.dp)),
        contentPadding = PaddingValues(vertical = 16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = buttonText,
            style = TextStyle(color = colorResource(id = R.color.white), fontSize = 16.sp)
        )
        Spacer(modifier = Modifier.width(80.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = "icon arrow Right"
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    ChatAppC38GOnlineTheme {
        LoginContent(navigator = object : Navigator {
            override fun openHomeActivity() {

            }

            override fun openRegisterActivity() {
            }

        })
    }
}