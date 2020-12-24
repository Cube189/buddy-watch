package me.gmur.buddywatch.justwatch.api

class Context {

    private val values = HashMap<Key, Array<String>>()

    operator fun set(key: Key, value: String) {
        values[key] = arrayOf(value)
    }

    operator fun set(key: Key, value: Array<String>) {
        values[key] = value
    }

    operator fun get(key: Key): Array<String> {
        return values[key] ?: arrayOf()
    }

    enum class Key {
        PROVIDER,
        REGION,
    }
}
