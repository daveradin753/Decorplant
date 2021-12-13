package com.example.decor_plant.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Disease(
    var id: String? = null,
    var name: String? = null,
    var image: String? = null,
    var solution: String? = null
) : Parcelable