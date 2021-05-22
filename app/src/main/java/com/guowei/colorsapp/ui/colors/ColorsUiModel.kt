package com.guowei.colorsapp.ui.colors

import android.graphics.Color

data class ColorsUiModel(val current: String, val colorSet: List<String>) {

    private val currentIndex by lazy {
        colorSet.indexOf(current)
    }

    val prevButtonEnabled by lazy {
        currentIndex > 0
    }

    val nextButtonEnabled by lazy {
        currentIndex < colorSet.size - 1
    }

    val bgColor by lazy {
        Color.parseColor(current)
    }

    val next by lazy {
        if (currentIndex == colorSet.size - 1) {
            null
        } else {
            copy(current = colorSet[currentIndex + 1])
        }
    }

    val previous by lazy {
        if (currentIndex == 0) {
            null
        } else {
            copy(current = colorSet[currentIndex - 1])
        }
    }
}