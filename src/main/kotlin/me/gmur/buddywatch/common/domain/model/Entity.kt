package me.gmur.buddywatch.common.domain.model

abstract class Entity<T : Id<*>> {

    abstract val id: T

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        if (
            javaClass != other?.javaClass ||
            id != (other as Entity<*>).id
        ) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString() = "${this.javaClass.simpleName}($id)"
}

abstract class Id<T : Comparable<T>> : Comparable<Id<T>> {
    open val value: T get() = throw IllegalStateException("Object has not been persisted")

    override fun compareTo(other: Id<T>) = value.compareTo(other.value)

    override fun toString() = value.toString()
}
