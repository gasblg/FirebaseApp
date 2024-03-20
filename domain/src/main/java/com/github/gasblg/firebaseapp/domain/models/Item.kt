package com.github.gasblg.firebaseapp.domain.models

import java.io.Serializable

data class Item(
    val id: String,
    val name: String,
    val description: String
) : Serializable {
    constructor() : this("", "", "")
}
