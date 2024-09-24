package com.example.sw5ecompanionapp.customization

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CustomizationOption(
    val name: String="Empty Name",
    val preq: String="",
    val source: String="SRC",
    val text: CharSequence="Model text"): Parcelable

fun CustomizationOption.isNotEmpty(): Boolean{
    return this.name!="Empty Name"
}

fun CustomizationOption.isEmpty(): Boolean{
    return this.name=="Empty Name"
}

fun CustomizationOption.hasPreq(): Boolean{
    return this.preq!=""
}