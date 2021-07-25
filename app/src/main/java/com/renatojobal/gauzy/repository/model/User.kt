package com.renatojobal.gauzy.repository.model

import android.net.Uri

data class User(
    val uid: String, // id in teh firebase auth
    val displayName: String,
    val email: String,
    var photoURL: String = "" // Uri of photo profile Microsoft do not provide photoUrl
)
