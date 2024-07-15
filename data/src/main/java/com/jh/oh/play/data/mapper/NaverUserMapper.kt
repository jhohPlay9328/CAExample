package com.jh.oh.play.data.mapper

import com.jh.oh.play.data.model.NaverUserRes
import com.jh.oh.play.domain.model.database.NaverUserResModel

fun mapperNaverUser(naverUserRes: NaverUserRes) = NaverUserResModel(
    naverUserRes.resultcode,
    naverUserRes.message,
    NaverUserResModel.UserData(
        naverUserRes.response.id,
        naverUserRes.response.nickname,
        naverUserRes.response.name,
        naverUserRes.response.email,
        naverUserRes.response.gender,
        naverUserRes.response.age,
        naverUserRes.response.birthday,
        naverUserRes.response.profile_image,
        naverUserRes.response.birthyear,
        naverUserRes.response.mobile,
    ),
)