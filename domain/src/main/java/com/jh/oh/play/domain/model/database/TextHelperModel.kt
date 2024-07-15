package com.jh.oh.play.domain.model.database


data class TextHelperModel (
    val id: Int,
    val positionX: Int,
    val positionY: Int,
    val text: String,
    val textSize: Float,
    val isBold: Boolean,
    val textColor: Int,
    val strokeSize: Int,
    val strokeColor: Int,
    val insidePadding: Int,
    val backgroundColor: Int,
)