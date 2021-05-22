package com.guowei.colorsapp.ui.colors

import android.graphics.Color

data class ColorsUiModel(
    // current color on the server
    val currentColor: String?,
    // color displayed currently chosen by user
    val chosenColor: String?,
    val colorSet: List<String>?,
    val isLoading: Boolean
) {

    private val currentIndex by lazy {
        colorSet?.indexOf(chosenColor) ?: -1
    }

    val prevButtonEnabled by lazy {
        !isLoading && currentIndex > 0
    }

    val nextButtonEnabled by lazy {
        !isLoading && colorSet?.let {
            currentIndex < it.size - 1
        } ?: false
    }

    val bgColor by lazy {
        chosenColor?.let {
            Color.parseColor(it)
        } ?: Color.WHITE
    }

    val setButtonVisible by lazy {
        !isLoading && !currentColor.equals(chosenColor, ignoreCase = true)
    }

    val next by lazy {
        colorSet?.let {
            if (currentIndex == it.size - 1) {
                null
            } else {
                copy(chosenColor = it[currentIndex + 1])
            }
        }
    }

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