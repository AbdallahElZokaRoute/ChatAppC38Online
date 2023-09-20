package com.route.chatappc38gonline.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.route.chatappc38gonline.AppUser
import com.route.chatappc38gonline.database.getUserFromFirestoreDB

class LoginViewModel : ViewModel() {
    val emailState = mutableStateOf("")
    val emailError = mutableStateOf("")
    val passwordState = mutableStateOf("")
    val passwordError = mutableStateOf("")
    val showLoading = mutableStateOf(false)
    val message = mutableStateOf("")
    val auth = Firebase.auth
    var navigator: Navigator? = null
    fun validateFields(): Boolean {
        if (emailState.value.isEmpty() || emailState.value.isBlank()) {
            emailError.value = "Email Required"
            return false
        } else
            emailError.value = ""
        if (passwordState.value.isEmpty() || passwordState.value.isBlank()) {
            passwordError.value = "Password Required"
            return false
        } else
            passwordError.value = ""
        return true
    }

    fun sendAuthDataToFirebase() {
        if (validateFields()) {
            showLoading.value = true
            registerUserToAuth()
        }
    }

    fun registerUserToAuth() {
        auth.signInWithEmailAndPassword(emailState.value, passwordState.value)
            .addOnCompleteListener {
                showLoading.value = false
                if (it.isSuccessful) {
                    // navigate to home Screen

                    // Add User to cloud Firestore
                    getUserFromFirestore(it.result.user?.uid)

                } else {
                    //show Error Dialog
                    message.value = it.exception?.localizedMessage ?: ""
                    Log.e("TAG", "registerUserToAuth: ${it.exception?.localizedMessage}")
                }
            }
    }

    fun navigateToRegisterScreen() {
        navigator?.openRegisterActivity()
    }

    fun getUserFromFirestore(uid: String?) {
        showLoading.value = true
        getUserFromFirestoreDB(
            uid!!, onSuccessListener = {
                val appUser = it.toObject(AppUser::class.java)
                // callback
                navigator?.openHomeActivity()
                showLoading.value = false
            }, onFailureListener = {
                showLoading.value = false
                message.value = it.localizedMessage ?: ""
            })
    }


}