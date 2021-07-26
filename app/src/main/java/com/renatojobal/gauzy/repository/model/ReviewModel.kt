package com.renatojobal.gauzy.repository.model

import com.google.firebase.firestore.DocumentReference

data class ReviewModel(
    val comment: String,
    val score: Double,
    var user: DocumentReference, // USER MODEL
    var userDisplayName: String
)
