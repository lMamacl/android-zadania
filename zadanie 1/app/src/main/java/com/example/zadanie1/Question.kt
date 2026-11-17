package com.example.zadanie1

import androidx.annotation.StringRes

data class Question(
    @get:StringRes val questionId: Int,
    val trueAnswer: Boolean
)
