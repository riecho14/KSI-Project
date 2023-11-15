package com.sil.ecohero

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Trash (
    val name: String,
    val photo: String,
    val desc: String
): Parcelable