package com.renatojobal.gauzy.repository.model

import android.os.Parcelable
import java.io.Serializable

data class ComponentModel(
    var docId: String = "",
    var score: Double,
    val career: String,
    val name: String,
    var reviewsNumber: Long
) : Serializable {

}
