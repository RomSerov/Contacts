package com.example.decomposesample.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
    val id: Int = -1,
    val userName: String,
    val phone: String
): Parcelable
