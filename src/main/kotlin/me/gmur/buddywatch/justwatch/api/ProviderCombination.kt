package me.gmur.buddywatch.justwatch.api

import me.gmur.buddywatch.justwatch.api.Context.Key.PROVIDER

class ProviderCombination(val providers: Collection<Provider>) {

    internal lateinit var context: Context

    fun titles(): Titles {
        context[PROVIDER] = providers.map { it.shorthand }.toTypedArray()

        return Titles(context)
    }
}
