package com.renatojobal.gauzy.repository.model

data class ReviewModel(
    val comment : String,
    val score : Long,
    val user : String // TODO: CHANGE TO USER MODEL
)
