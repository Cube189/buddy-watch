package me.gmur.buddywatch.justwatch.api

class RequestUnsuccessful(url: String, code: Int) : RuntimeException("Server returned code [$code] for [$url].")
