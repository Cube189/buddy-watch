package me.gmur.buddywatch.group.domain.model

import org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric

data class GroupUrl(val value: String = (0..2).joinToString("-") { randomAlphanumeric(4) }) {

    override fun toString(): String {
        return value
    }
}
