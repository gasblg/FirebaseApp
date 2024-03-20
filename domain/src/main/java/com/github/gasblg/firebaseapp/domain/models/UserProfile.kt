package com.github.gasblg.firebaseapp.domain.models


data class UserProfile(
    val uid: String? = "",
    val name: String? = "",
    val email: String? = "",
    val photo: String? = "",
    val dateCreated: String?,
    val dateSignedIn: String?
) {
    constructor() : this(
        "",
        "",
        "",
        "",
        "",
        ""
    )
}
