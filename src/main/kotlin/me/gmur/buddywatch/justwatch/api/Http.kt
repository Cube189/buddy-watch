package me.gmur.buddywatch.justwatch.api

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class Http constructor(
    private val url: String = BASE_URL,
    private val body: Map<String, Any>? = null
) {

    companion object {
        const val BASE_URL = "https://apis.justwatch.com/content/"
        private val RESULTS_PER_PAGE = resultsPerPageEnv()

        private fun resultsPerPageEnv(): Int {
            val env = System.getenv("RESULTS_PER_PAGE")

            return if (env.isNullOrBlank()) env.toInt() else 100
        }
    }

    private val json = Gson()

    fun path(path: String): Http {
        return Http(url + path, body)
    }

    fun body(body: Map<JwFilterParam, Any>): Http {
        val withPageSize = body.toMutableMap().apply { putIfAbsent(JwFilterParam.PAGE_SIZE, RESULTS_PER_PAGE) }
        val withPlainTextKeys = withPageSize.map { it.key.toPlain() to it.value }.toMap()

        return Http(url, withPlainTextKeys)
    }

    fun <T : Any> execute(type: TypeToken<T>): T {
        val result = execute()

        return json.fromJson(result, type.type)
    }

    fun <T : Any> executeWithResultCount(type: TypeToken<T>): Pair<Int, T> {
        val result = execute()

        val parsed = json.fromJson(result, JsonObject::class.java)
        val total = parsed.get("total_results").toString().toInt()

        val mapped = json.fromJson<T>(parsed.get("items"), type.type)

        return Pair(total, mapped)
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

        return if (body != null) {
            val requestBody = json.toJson(body, object : TypeToken<Map<String, Any>>() {}.type)
            request.post(requestBody.toRequestBody("application/json".toMediaType())).build()
        } else {
            request.build()
        }
    }
}
