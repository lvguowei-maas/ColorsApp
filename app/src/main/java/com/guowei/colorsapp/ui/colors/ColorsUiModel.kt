package com.guowei.colorsapp.ui.colors

import android.graphics.Color

data class ColorsUiModel(
    // current color set in the server
    val currentColor: String,
    // color displayed currently chosen by user
    val chosenColor: String,
    val colorSet: List<String>
) {

    private val currentIndex by lazy {
        colorSet.indexOf(chosenColor)
    }

    val prevButtonEnabled by lazy {
        currentIndex > 0
    }

    val nextButtonEnabled by lazy {
        currentIndex < colorSet.size - 1
    }

    val bgColor by lazy {
        Color.parseColor(chosenColor)
    }

    val setButtonVisible by lazy {
        !currentColor.equals(chosenColor, ignoreCase = true)
    }

    val next by lazy {
        if (currentIndex == colorSet.size - 1) {
            null
        } else {
            copy(chosenColor = colorSet[currentIndex + 1])
        }
    }

    val previous by lazy {
        if (currentIndex == 0) {
            null
        } else {
            copy(chosenColor = colorSet[currentIndex - 1])
        }
    }
}