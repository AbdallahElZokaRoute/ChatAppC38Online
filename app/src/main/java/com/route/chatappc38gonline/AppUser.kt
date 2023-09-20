package com.route.chatappc38gonline

data class AppUser(
    val id: String? = null,
    val firstName: String? = null,
    val email: String? = null
) {
    companion object {
        const val COLLECTION_NAME = "Users"
    }
}
