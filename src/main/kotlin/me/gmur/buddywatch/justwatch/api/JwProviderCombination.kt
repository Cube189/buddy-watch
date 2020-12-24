package me.gmur.buddywatch.justwatch.api

import me.gmur.buddywatch.justwatch.api.Context.Key.PROVIDER

class JwProviderCombination(val providers: Collection<JwProvider>) {

    internal lateinit var context: Context

    fun titles(): JwTitles {
        context[PROVIDER] = providers.map { it.shorthand }.toTypedArray()

        return JwTitles(context)
    }
}
