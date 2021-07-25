package com.renatojobal.gauzy.repository.model

import com.google.firebase.firestore.DocumentReference

data class ReviewModel(
    val comment: String,
    val score: Float,
    var user: DocumentReference? = null, // USER MODEL
    var userDisplayName: String = ""
)
