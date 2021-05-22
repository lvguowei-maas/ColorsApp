package com.guowei.colorsapp.ui.colors

import android.graphics.Color
import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ColorsUiModel(
    // current color on the server
    val currentColor: String?,
    // color displayed currently chosen by user
    val chosenColor: String?,
    val colorSet: List<String>?,
    val isLoading: Boolean
) : Parcelable {

    @IgnoredOnParcel
    private val currentIndex by lazy {
        colorSet?.indexOf(chosenColor) ?: -1
    }

    @IgnoredOnParcel
    val prevButtonVisible by lazy {
        !isLoading && currentIndex > 0
    }

    @IgnoredOnParcel
    val nextButtonVisible by lazy {
        !isLoading && colorSet?.let {
            currentIndex < it.size - 1
        } ?: false
    }

    @IgnoredOnParcel
    val bgColor by lazy {
        chosenColor?.let {
            Color.parseColor(it)
        } ?: Color.WHITE
    }

    @IgnoredOnParcel
    val setButtonVisible by lazy {
        !isLoading && !currentColor.equals(chosenColor, ignoreCase = true)
    }

    @IgnoredOnParcel
    val next by lazy {
        colorSet?.let {
            if (currentIndex == it.size - 1) {
                null
            } else {
                copy(chosenColor = it[currentIndex + 1])
            }
        }
    }

    @IgnoredOnParcel
    val previous by lazy {
        colorSet?.let {
            if (currentIndex == 0) {
                null
            } else {
                copy(chosenColor = it[currentIndex - 1])
            }
        }
    }
}