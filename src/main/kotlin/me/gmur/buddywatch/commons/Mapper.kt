package me.gmur.buddywatch.commons

interface Mapper<D, E> {

    fun mapToEntity(source: D): E

    fun mapToEntity(source: D, base: E): E

    fun mapToDomain(source: E): D
}
