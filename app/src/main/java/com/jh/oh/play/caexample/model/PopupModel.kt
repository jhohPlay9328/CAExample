package com.jh.oh.play.caexample.model

// 방송 입장 및 방송 화면에서 사용하는 data

data class PopupModel(
    var dialogState: Int = 0,
    var pageCode: Int = 0,
    var item: Any? = null
)