package com.jh.oh.play.data.mapper

import com.jh.oh.play.data.model.database.TextHelperRes

fun mapperTextHelperList(textHelperResList: List<TextHelperRes>) = textHelperResList.map { textHelperRes ->
    mapperTextHelper(textHelperRes)
}