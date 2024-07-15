package com.jh.oh.play.data.mapper

import com.jh.oh.play.data.model.database.TextHelperRes
import com.jh.oh.play.domain.model.database.TextHelperModel

fun mapperTextHelper(textHelperRes: TextHelperRes) = TextHelperModel(
    textHelperRes.id,
    textHelperRes.positionX,
    textHelperRes.positionY,
    textHelperRes.text,
    textHelperRes.textSize,
    textHelperRes.isBold,
    textHelperRes.textColor,
    textHelperRes.strokeSize,
    textHelperRes.strokeColor,
    textHelperRes.insidePadding,
    textHelperRes.backgroundColor,
)