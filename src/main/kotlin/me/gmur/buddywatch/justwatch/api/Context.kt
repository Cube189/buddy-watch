package me.gmur.buddywatch.justwatch.api

class Context {

    private val values = HashMap<Key, String>()

    operator fun set(key: Key, value: String) {
        values[key] = value
    }

    operator fun get(key: Key): String {
        return values[key] ?: ""
    }

    enum class Key {
        PROVIDER,
        REGION,
    }
}
