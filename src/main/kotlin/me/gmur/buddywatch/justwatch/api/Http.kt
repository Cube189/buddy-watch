package me.gmur.buddywatch.justwatch.api

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class Http constructor(
    private val url: String = BASE_URL,
    private val body: String? = null
) {

    companion object {
        const val BASE_URL = "https://apis.justwatch.com/content/"
    }

    private val json = Gson()

    fun path(path: String): Http {
        return Http(url + path, body)
    }

    fun body(body: String): Http {
        return Http(url, body)
    }

    fun <T : Any> execute(type: TypeToken<T>): T {
        val result = execute()

        return json.fromJson(result, type.type)
    }

    fun execute(): String {
        val client = OkHttpClient()
        val request = request()

        val response = client.newCall(request).execute()

        if (!response.isSuccessful) throw RequestUnsuccessful(request.url.toString(), response.code)

        return response.body!!.string()
    }

    private fun request(): Request {
        val request = Request.Builder()
            .header("User-Agent", "BuddyWatch")
            .url(url)

        return if (body != null)
            request.post(body.toRequestBody("application/json".toMediaType())).build()
        else
            request.build()
    }
}
