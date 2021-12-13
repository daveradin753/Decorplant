package com.example.decor_plant.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class History(
    var uid: String? = null,
    var id: String? = null,
    var name: String? = null,
    var image: String? = null,
    var solution: String? = null,
    var date: String? = null,
    var bookmarked: Boolean? = false
) : Parcelable
