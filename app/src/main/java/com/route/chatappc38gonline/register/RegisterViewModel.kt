package com.route.chatappc38gonline.register

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.route.chatappc38gonline.model.AppUser
import com.route.chatappc38gonline.database.addUserToFirestoreDB

class RegisterViewModel : ViewModel() {
    val firstNameState = mutableStateOf("")
    val firstNameError = mutableStateOf("")
    val emailState = mutableStateOf("")
    val emailError = mutableStateOf("")
    val passwordState = mutableStateOf("")
    val passwordError = mutableStateOf("")
    val showLoading = mutableStateOf(false)
    val message = mutableStateOf("")
    val auth = Firebase.auth
    fun validateFields(): Boolean {
        if (firstNameState.value.isEmpty() || firstNameState.value.isBlank()) {
            firstNameError.value = "First name required"
            return false
        } else {
            firstNameError.value = ""
        }
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
        auth.createUserWithEmailAndPassword(emailState.value, passwordState.value)
            .addOnCompleteListener {
                showLoading.value = false
                if (it.isSuccessful) {
                    // navigate to home Screen

                    // Add User to cloud Firestore
                    addUserToFirestore(it.result.user?.uid)

                } else {
                    //show Error Dialog
                    message.value = it.exception?.localizedMessage ?: ""
                    Log.e("TAG", "registerUserToAuth: ${it.exception?.localizedMessage}")
                }
            }
    }

    fun addUserToFirestore(uid: String?) {
        showLoading.value = true
        addUserToFirestoreDB(
            AppUser(
                id = uid,
                firstName = firstNameState.value,
                email = emailState.value
            ), onSuccessListener = {
                message.value = "Successful Registration"
                showLoading.value = false
            }, onFailureListener = {
                showLoading.value = false
                message.value = it.localizedMessage ?: ""
            })
    }


}