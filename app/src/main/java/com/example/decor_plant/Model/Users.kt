package com.example.decor_plant.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Users(
    var uid: String? = null,
    var email: String? = null,
    var name: String? = null
) : Parcelable
