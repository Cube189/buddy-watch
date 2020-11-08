package me.gmur.buddywatch.group.domain.model

data class Provider(
    val shorthand: String,
    val name: String,
    val id: Long? = null
)
