package me.gmur.buddywatch.justwatch.api

enum class FilterParam {
    AGE_CERTIFICATIONS,
    CONTENT_TYPES,
    PRESENTATION_TYPES,
    PROVIDERS,
    GENRES,
    LANGUAGES,
    RELEASE_YEAR_FROM,
    RELEASE_YEAR_UNTIL,
    MONETIZATION_TYPES,
    MIN_PRICE,
    MAX_PRICE,
    NATIONWIDE_CINEMA_RELEASES_ONLY,
    SCORING_FILTER_TYPES,
    CINEMA_RELEASE,
    QUERY,
    PAGE,
    PAGE_SIZE,
    TIMELINE_TYPE,
    PERSON_ID;

    fun toPlain(): String {
        return name.toLowerCase()
    }
}
