package com.jh.oh.play.data.common.util

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import com.google.gson.Gson
import com.jh.oh.play.data.di.NetworkModule
import com.jh.oh.play.data.model.database.TextHelperRes
import com.jh.oh.play.domain.entity.UIState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.FlowCollector
import org.json.JSONObject
import retrofit2.Response
import java.net.SocketTimeoutException


object RetrofitUtil {
    private const val MEDIA_TYPE = "application/x-www-form-urlencoded"
    private const val CHARSET_UTF8 = "UTF-8"
    private const val URL_PREFIX_TYPE = "type="
    private const val URL_PREFIX_PAGE = "&page="
    private const val URL_PREFIX_DATA = "data="
    private const val URL_PREFIX_KEY = "&key="
    private const val URL_PREFIX_VER = "&v="

    private const val URL_VALUE_VER = 2

    private const val KEY_RST_CODE = "rstCode"
    private const val KEY_RST_CODE_NAVER = "resultcode"
    private const val KEY_RST_CODE_PRESET = "result"

    const val RST_CODE_SUCCESS = "0"
    const val RST_CODE_SUCCESS_NAVER = "00"
    const val RST_CODE_SUCCESS_PRESET = ""

    private const val API_TYPE_POPKON = 0
    const val API_TYPE_NAVER = 1
    const val API_TYPE_PRESET = 2

    fun getUserAgentValue(context: Context): String {
        val packageManger = context.packageManager
        val packageInfo = packageManger.getPackageInfo(
            context.packageName,
            PackageManager.GET_GIDS or PackageManager.GET_META_DATA
        )
        val packageName = packageInfo.packageName
        val versionName = packageInfo.versionName

        return "${NetworkModule.USER_AGENT_VALUE} ${Build.MODEL} $packageName $versionName"
    }

    fun createStringToTextPresetModel(jsonString: String): TextHelperRes =
        Gson().fromJson(jsonString, TextHelperRes::class.java)

    inline fun <reified RESPONSE, reified MODEL>responseEmit(
        callbackId: Enum<*>,
        response: Response<RESPONSE>,
        mapper: (response: RESPONSE) -> MODEL
    ): UIState {
        return when(response.isSuccessful) {
            true -> {
                val decodeResponse = response.body()

//                Log.d("OG", "Retrofit Test responseNaverApiEmit callbackId: $callbackId / decodeResponse : $decodeResponse")

                when(getResultCode(decodeResponse, API_TYPE_NAVER)) {
                    RST_CODE_SUCCESS_NAVER ->
                        UIState.ResultSuccess(callbackId, decodeResponse)
                    else -> UIState.ResultFail(callbackId, decodeResponse)
                }
            }
            else -> UIState.NetworkFail(callbackId, response.code().toString())
        }
    }

    inline fun <reified RESPONSE, reified MODEL>responseRoomEmit(
        callbackId: Enum<*>,
        response: Any,
        mapper: (response: RESPONSE) -> MODEL
    ): UIState {
        Log.d("OG", "Retrofit Test responseRoomEmit callbackId: $callbackId / response : $response")
        val mapperModel = mapper(response as RESPONSE)

        Log.d("OG", "Retrofit Test responseRoomEmit callbackId: $callbackId / mapperModel : $mapperModel")
        return UIState.ResultSuccess(callbackId, mapperModel)
    }

    suspend inline fun retryRetrofit(
        callbackId: Enum<*>,
        cause: Throwable,
        attempt: Long,
        flowCollector: FlowCollector<UIState>,
    ): Boolean {
        cause.printStackTrace()

        return when {
            (cause is SocketTimeoutException).not() && (attempt < 2) -> {
                delay(10_000)
                true
            }
            else -> networkFail(callbackId, cause, flowCollector)
        }
    }

    suspend inline fun networkFail(
        callbackId: Enum<*>,
        cause: Throwable,
        flowCollector: FlowCollector<UIState>,
    ): Boolean {
        when {
            cause.message.isNullOrEmpty() ->
                flowCollector.emit(
                    UIState.NetworkFail(
                        callbackId,
                        "Unknown exception"
                    )
                )
            else -> flowCollector.emit(UIState.NetworkFail(callbackId, cause.message!!))
        }
        return false
    }

    fun <T>getResultCode(decodeData: T, apiType: Int = API_TYPE_POPKON): String {
        val jsonObject = JSONObject(Gson().toJson(decodeData))
        return findJSONObject(
            jsonObject,
            // 팝콘티비 API 응답 코드 key : "rstCode"
            // 네이버 API 응답 코드 key : "resultcode"
            when(apiType){
                API_TYPE_PRESET -> KEY_RST_CODE_PRESET
                API_TYPE_NAVER -> KEY_RST_CODE_NAVER
                else -> KEY_RST_CODE
            },
        )
    }

    private fun findJSONObject(
        jsonObject: JSONObject,
        findKey: String,
    ): String {
        // 팝콘티비 API 성공 응답 value : "0"
        // 네이버 API 성공 응답 value : "00"
        var resultValue = ""

        val keyIterator = jsonObject.keys()
        val map: MutableMap<String, Any> = HashMap()

        while (keyIterator.hasNext()) {
            val key = keyIterator.next().toString()
            map[key] = jsonObject.get(key)
        }
        for ((key) in map) {
            when (key){
                (findKey) -> return jsonObject.getString(key)
                else -> {
                    when (val value = jsonObject[key]) {
                        is JSONObject -> {
                            resultValue = findJSONObject(value, findKey)
                            if(resultValue.isNotEmpty()) return resultValue
                        }
                    }
                }
            }
        }

        return resultValue
    }
}