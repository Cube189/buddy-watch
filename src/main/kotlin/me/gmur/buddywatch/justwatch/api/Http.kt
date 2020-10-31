package me.gmur.buddywatch.justwatch.api

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request

class Http constructor(private val url: String = BASE_URL) {

    companion object {
        const val BASE_URL = "https://apis.justwatch.com/content/"
    }

    private val json = Gson()

    fun path(path: String): Http {
        return Http(url + path)
    }

    fun <T : Any> execute(type: TypeToken<T>): T {
        val result = execute()

        return json.fromJson(result, type.type)
    }

    fun execute(): String {
        val client = OkHttpClient()
        val request = Request.Builder()
            .header("User-Agent", "BuddyWatch")
            .url(url)
            .build()

        val response = client.newCall(request).execute()

        if (!response.isSuccessful) throw RequestUnsuccessful()

        return response.body!!.string()
    }
}
