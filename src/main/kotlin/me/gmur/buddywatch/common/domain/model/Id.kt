package me.gmur.buddywatch.common.domain.model

abstract class Id<T : Comparable<T>> : Comparable<Id<T>> {
    open val value: T get() = throw IllegalStateException("Object has not been persisted")

    override fun compareTo(other: Id<T>) = value.compareTo(other.value)

    override fun toString() = value.toString()
}
