package me.gmur.buddywatch.group.domain.model

import org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric

data class GroupUrl(private val url: String = (0..3).joinToString("-") { randomAlphanumeric(4) }) {

    override fun toString(): String {
        return url
    }
}
