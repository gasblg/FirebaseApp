package com.github.gasblg.firebaseapp.domain.models


data class UserProfile(
    var uid: String? = "",
    var name: String? = "",
    var email: String? = "",
    var photo: String? = ""
)  {
    constructor() : this(
        "",
        "",
        "",
        ""
    )
}
