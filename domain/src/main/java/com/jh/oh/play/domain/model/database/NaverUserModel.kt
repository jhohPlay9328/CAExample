package com.jh.oh.play.domain.model.database



// https://developers.naver.com/docs/login/profile/profile.md
// HTTP 코드	    에러 코드	        에러 메시지	                                                  조치 방안
// 401	            024	            Authentication failed / 인증에 실패했습니다.
// 401	            028	            Authentication header not exists /
//                              OAuth 인증 헤더(authorization header)가 없습니다.
// 403	            403	            Forbidden / 호출 권한이 없습니다.	                    API 요청 헤더에 클라이언트 ID와 Secret 값을 정확히 전송했는지 확인해보시길 바랍니다.
// 404	            404	            Not Found / 검색 결과가 없습니다.
// 500	            500	        Internal Server Error / 데이터베이스 오류입니다.	        서버 내부 에러가 발생하였습니다. 포럼에 올려주시면 신속히 조치하겠습니다.
data class NaverUserResModel(
    val resultcode: String, // API 호출 결과 코드
    val message: String, // 호출 결과 메시지
    val response: UserData,
) {
    data class UserData(
        val id: String,
        val nickname: String,
        val name: String,
        val email: String,
        val gender: String,
        val age: String,
        val birthday: String,
        val profile_image: String,
        val birthyear: String,
        val mobile: String,
    )
}


