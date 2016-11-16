package com.yeungkc.gank.io.model.Interceptor

import android.util.Pair
import com.orhanobut.logger.Logger
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.internal.http.RealResponseBody
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.nio.charset.Charset

class HttpLogInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
//        var interParam: String? = null
        val original = chain.request()

        Logger.d(original.url())

        val requestBuilder = original.newBuilder()

        if (original.body() is FormBody) {
            val newFormBody = FormBody.Builder()
            val oldFormBody = original.body() as FormBody
            for (i in 0..oldFormBody.size() - 1) {
                var value = oldFormBody.encodedValue(i)
                if (i == 0) {
                    value = URLDecoder.decode(value, "UTF-8")
//                    interParam = value
                }
                newFormBody.addEncoded(oldFormBody.encodedName(i), value)
            }

            //            addParams(interParam, newFormBody);

            val build = newFormBody.build()

            printRequestLog(build)

            requestBuilder.method(original.method(), build)
        }

        val request = requestBuilder.build()
        val response = chain.proceed(request)

        val body = response.body()
        val json = printResponseLog(body)

        return transformerData(response, json)
    }


    @Throws(UnsupportedEncodingException::class)
    private fun printRequestLog(build: FormBody) {
        val str = build.encodedValue(0)

        val decode = URLDecoder.decode(str, "UTF-8")

        val json = "{Request:$decode}"

        try {
            Logger.json(json)
        } catch (e: Exception) {
            Logger.e("Json serialization failure\n\n" + json)
        }
    }

    @Throws(IOException::class)
    private fun printResponseLog(responseBody: ResponseBody): String {
        var rawJson: String? = null

        val contentLength = responseBody.contentLength()

        if (contentLength != 0L) {
            val source = responseBody.source()
            source.request(java.lang.Long.MAX_VALUE)
            val buffer = source.buffer()

            rawJson = buffer.clone().readString(Charset.forName("UTF-8"))
            val json = "{Response:$rawJson}"

//            Logger.d(json)

            try {
                Logger.json(json)
            } catch (e: Exception) {
                Logger.e("Json serialization failure\n\n" + json)
            }

        }
        return rawJson!!
    }

    fun transformerData(response: Response, json: String): Response {
        val booleanStringPair = transformerJson(json)
        if (booleanStringPair.first) {
            val source = response.body().source()
            val buffer = source.buffer()
            buffer.clear()
            buffer.writeString(booleanStringPair.second, Charset.forName("UTF-8"))
            return response.newBuilder().body(RealResponseBody(response.headers(), source)).build()
        }
        return response
    }

    fun transformerJson(json: String): Pair<Boolean, String> {
        return Pair(false, json)
    }
}
